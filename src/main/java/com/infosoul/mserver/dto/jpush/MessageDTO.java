package com.infosoul.mserver.dto.jpush;

import java.io.Serializable;
import java.util.Map;

/**
 * jpush message
 *
 * @author longxy
 * @date 2018-06-13 23:18
 */
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 1592841382257122500L;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 扩展信息
     */
    private Map<String, String> extras;

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }
}
