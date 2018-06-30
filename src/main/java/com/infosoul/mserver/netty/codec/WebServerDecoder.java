package com.infosoul.mserver.netty.codec;

import java.util.List;

import com.infosoul.mserver.common.utils.NettyUtils;
import com.infosoul.mserver.enums.FrameTypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * time server 解码
 *
 * @author xiangyi.long
 * @date 2018-06-20 09:20
 */
public class WebServerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 开头字节
        in.readByte();
        // 命令码
        int code = in.readByte();
        if (code == FrameTypeEnum.LOGIN.getCode()) {
            NettyUtils.loginDecode(in, out);
        } else if (code == FrameTypeEnum.HEARTBEAT.getCode()) {
            NettyUtils.heartBeatDecode(in, out);
        } else if (code == FrameTypeEnum.SENSOR_INFO.getCode()) {

        } else if (code == FrameTypeEnum.SENSOR_DATA.getCode()) {

        }

    }


}
