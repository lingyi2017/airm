package com.infosoul.mserver.netty;

import com.infosoul.mserver.common.utils.NettyUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * client handler
 *
 * @author xiangyi.long
 * @date 2018-06-14 14:04
 */
public class WebClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // NettyUtils.login();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            // 读空闲
            if (event.state().equals(IdleState.READER_IDLE)) {
                //ctx.close();
                // 写空闲
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                //ctx.close();
                // 都空闲
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                NettyUtils.sendHeartBeat(ctx);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
