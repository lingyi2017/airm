package com.infosoul.mserver.websocket;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import com.infosoul.mserver.dto.web.WsPushDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.infosoul.mserver.dto.web.DeviceMapDTO;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.service.airm.DeviceService;

/**
 * 地图 WebSocket
 *
 * @author longxy
 * @date 2018-06-25 21:41
 */
@ServerEndpoint(value = "/websocket/map", configurator = SpringConfigurator.class)
public class MapWebsocket {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapWebsocket.class);

    private static Map<String, MapWebsocket> clients = new ConcurrentHashMap<>();

    @Autowired
    private DeviceService deviceService;

    /**
     * 某个客户端连接的session
     */
    private Session session;

    /**
     * 连接成功调用的方法
     *
     * @param session 客户端会话标识
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        clients.put(session.getId(), this);
        push(findAllDevices());

        System.out.println("new client connect, session id : " + session.getId());
    }

    /**
     * 收到客户消息调用的方法
     *
     * @param message 客户端消息
     * @param session
     */
    @OnMessage
    @SuppressWarnings("unchecked")
    public void onMessage(String message, Session session) {
        System.out.println("client session id " + session.getId());
        System.out.println("client message " + message);
    }

    /**
     * 关闭连接调用的方法
     */
    @OnClose
    public void onClose() {
        close();
        System.out.println("one client leave, session id : " + session.getId());
    }

    /**
     * 发生错误调用的方法
     *
     * @param session 发生错误的会话
     * @param e 错误信息
     */
    @OnError
    public void onError(Session session, Throwable e) {
        System.out.println("server exception, session id : " + session.getId());
        close();
        LOGGER.error("web socket 异常", e);
    }

    /**
     * socket server 推送的消息
     *
     * @param deviceMapDTO
     */
    public static void socketServerPush(DeviceMapDTO deviceMapDTO) {
        if (null == deviceMapDTO) {
            return;
        }
        WsPushDTO wsPushDTO = new WsPushDTO();
        wsPushDTO.setType("2");
        wsPushDTO.setDevice(deviceMapDTO);
        Iterator<MapWebsocket> iterator = clients.values().iterator();
        while (iterator.hasNext()) {
            MapWebsocket mapWebsocket = iterator.next();
            mapWebsocket.push(JSON.toJSONString(wsPushDTO));
        }
    }

    /**
     * 推送消息至页面
     *
     * @param msg
     */
    private synchronized void push(String msg) {
        try {
            if (this.session.isOpen()) {
                if (StringUtils.isNotBlank(msg)) {
                    this.session.getBasicRemote().sendText(msg);
                }
            }
        } catch (IOException e) {
            LOGGER.error("web socket 数据推送异常", e.getMessage());
        }
    }

    /**
     * 关闭连接
     */
    private void close() {
        try {
            String sessionId = session.getId();
            if (!CollectionUtils.isEmpty(clients) && clients.containsKey(sessionId)) {
                clients.remove(sessionId);
            }
            session.close();
        } catch (Exception e) {
            LOGGER.error("web socket 关闭异常", e);
        }
    }

    /**
     * 所有设备
     * 
     * @return
     */
    private String findAllDevices() {
        List<Device> devices = deviceService.findAll(new Device());
        if (CollectionUtils.isEmpty(devices)) {
            return null;
        }
        List<DeviceMapDTO> dtos = Lists.newArrayList();
        for (Device device : devices) {
            DeviceMapDTO dto = new DeviceMapDTO();
            BeanUtils.copyProperties(device, dto);
            dtos.add(dto);
        }
        WsPushDTO wsPushDTO = new WsPushDTO();
        wsPushDTO.setType("1");
        wsPushDTO.setDevices(dtos);
        return JSON.toJSONString(wsPushDTO);
    }
}
