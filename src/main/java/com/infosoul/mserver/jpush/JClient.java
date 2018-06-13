package com.infosoul.mserver.jpush;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.infosoul.mserver.dto.jpush.MessageDTO;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;

/**
 * jpush
 *
 * @author xiangyi.long
 * @date 2018-06-12 10:22
 */
@Component
@Lazy
public class JClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(JClient.class);

    @Value("${jpush.masterSecret}")
    private String masterSecret;

    @Value("${jpush.appKey}")
    private String appKey;

    @Value("${jpush.alarm.tag}")
    private String tag;

    private MessageDTO messageDTO;

    public JClient initMessage(MessageDTO messageDTO) {
        this.messageDTO = messageDTO;
        return this;
    }

    public void push() {
        try {
            PushClient client = client();
            client.sendPush(getPushPayload());
            client.close();
        } catch (APIConnectionException e) {
            LOGGER.error("推送连接失败", e);
        } catch (APIRequestException e) {
            LOGGER.error("推送请求异常", e);
        }
    }

    private PushClient client() {
        ClientConfig config = ClientConfig.getInstance();
        // 开发环境
        config.setApnsProduction(false);
        return new PushClient(masterSecret, appKey, null, config);
    }

    private PushPayload getPushPayload() {
        return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.tag(tag))
                .setMessage(getMessage()).build();
    }

    private Message getMessage() {
        return Message.newBuilder().setMsgContent(messageDTO.getMsgContent()).setTitle(messageDTO.getTitle())
                .addExtras(messageDTO.getExtras()).build();
    }
}
