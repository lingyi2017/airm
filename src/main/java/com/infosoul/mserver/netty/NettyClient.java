package com.infosoul.mserver.netty;

import com.infosoul.mserver.dto.netty.FrameDTO;
import com.infosoul.mserver.netty.codec.WebClientEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * netty client
 *
 * @author xiangyi.long
 * @date 2018-06-14 13:57
 */
public class NettyClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

    private static ChannelFuture future;

    private static final int RETRY = 3;

    /**
     * 发送消息
     * 
     * @param dto
     */
    public static void send(FrameDTO dto) {
        retry();
        if (NettyClient.isOpen()) {
            future.channel().writeAndFlush(dto);
        }
    }

    /**
     * 连接重试
     */
    private static void retry() {
        int count = 0;
        while (count < RETRY) {
            if (NettyClient.isOpen()) {
                break;
            }

            NettyClient.connect();
            if (!NettyClient.isOpen()) {
                count++;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (RETRY == count) {
            LOGGER.info("netty 重连失败");
        }
    }

    /**
     * 连接通道是否开启
     * 
     * @return
     */
    private static Boolean isOpen() {
        if (null != future && null != future.channel() && future.channel().isOpen()) {
            return true;
        }
        return false;
    }

    /**
     * 连接
     */
    private static void connect() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("clientEncoder", new WebClientEncoder());
                    pipeline.addLast("heartbeat", new IdleStateHandler(120, 90, 30, TimeUnit.SECONDS));
                    pipeline.addLast("clientHandler", new WebClientHandler());
                }
            });

            future = b.connect("localhost", 8081).sync();
        } catch (Exception e) {
            LOGGER.error("netty client 连接异常", e);
        }
    }
}
