package com.infosoul.mserver.common.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.infosoul.mserver.dto.netty.FrameDTO;
import com.infosoul.mserver.enums.FrameTypeEnum;
import com.infosoul.mserver.netty.CRC16M;

import com.infosoul.mserver.netty.NettyClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

/**
 * netty工具类
 *
 * @author xiangyi.long
 * @date 2018-06-20 11:03
 */
public class NettyUtils {

    /**
     * web server 登录
     * 
     */
    public static void login() {
        FrameDTO dto = new FrameDTO();
        dto.setFrameType(FrameTypeEnum.LOGIN);
        dto.setDataLength(0x06);
        dto.setData(Constant.WEB_SERVER_ID);
        System.out.println("==登录协议帧==" + JSON.toJSONString(dto));
        NettyClient.send(dto);
    }

    /**
     * 登录编码
     *
     * @param dto
     * @param out
     */
    public static void loginEncode(FrameDTO dto, ByteBuf out) {
        ByteBuf buf = Unpooled.buffer();
        // 开头字节
        buf.writeByte(dto.getStart());
        // 协议类型
        buf.writeByte(dto.getFrameType().getCode());
        // 数据长度
        buf.writeByte(dto.getDataLength());
        // 数据内容
        String data = (String) dto.getData();
        buf.writeBytes(data.getBytes());
        // CRC16位校验和
        int crc16 = CRC16M.getCRC16(buf.array());
        buf.writeShort(crc16);
        out.writeBytes(buf);
    }

    /**
     * 登录解码
     *
     * @param in
     * @param out
     */
    public static void loginDecode(ByteBuf in, List<Object> out) {
        FrameDTO dto = new FrameDTO();
        // 数据长度
        int dataLength = in.readByte();
        // 协议数据
        byte[] bytes = new byte[dataLength];
        in.readBytes(bytes);
        dto.setDataLength(dataLength);
        dto.setData(new String(bytes));
        out.add(dto);
    }

    /**
     * 发送心跳包
     * 
     * @param ctx
     */
    public static void sendHeartBeat(ChannelHandlerContext ctx) {
        FrameDTO dto = new FrameDTO();
        dto.setFrameType(FrameTypeEnum.HEARTBEAT);
        dto.setDataLength(0x01);
        dto.setData(0x30);
        ctx.writeAndFlush(dto);
        System.out.println("==心跳协议帧==" + JSON.toJSONString(dto));
    }

    /**
     * 心跳编码
     *
     * @param dto
     * @param out
     */
    public static void heartBeatEncode(FrameDTO dto, ByteBuf out) {
        ByteBuf buf = Unpooled.buffer();
        // 开头字节
        buf.writeByte(dto.getStart());
        // 协议类型
        buf.writeByte(dto.getFrameType().getCode());
        // 数据长度
        buf.writeByte(dto.getDataLength());
        // 数据内容
        buf.writeByte((int) dto.getData());
        // CRC16位校验和
        int crc16 = CRC16M.getCRC16(buf.array());
        System.out.println("==心跳crc16==" + crc16);
        buf.writeShort(crc16);
        out.writeBytes(buf);
    }

    /**
     * 登录解码
     *
     * @param in
     * @param out
     */
    public static void heartBeatDecode(ByteBuf in, List<Object> out) {
        FrameDTO dto = new FrameDTO();
        // 数据长度
        int dataLength = in.readByte();
        // 协议数据
        int data = in.readByte();
        dto.setDataLength(dataLength);
        dto.setData(data);
        out.add(dto);
    }

}
