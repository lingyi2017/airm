package com.infosoul.mserver.netty;

import com.infosoul.mserver.common.utils.NettyUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * web server login socket server
 *
 * @author longxy
 * @date 2018-06-30 20:35
 */
@Component
public class WebClientLogin implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 设置在 root application context 初始化完执行
        if (event.getApplicationContext().getParent() == null) {
            NettyUtil.login();
        }
    }
}
