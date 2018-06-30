package com.infosoul.mserver.netty;

import com.alibaba.fastjson.JSON;
import com.infosoul.mserver.dto.netty.FrameDTO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * server handler
 *
 * @author xiangyi.long
 * @date 2018-06-14 11:39
 */
public class WebServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 连接成功调用
     * 
     * @param ctx
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {

    }

    /**
     * 收到消息调用
     * 
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        FrameDTO dto = (FrameDTO) msg;
        System.out.println("===收到客户端数据===" + JSON.toJSONString(dto));
    }

    /**
     * 异常时调用
     * 
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        // 关闭当前连接
        ctx.close();
    }

}
