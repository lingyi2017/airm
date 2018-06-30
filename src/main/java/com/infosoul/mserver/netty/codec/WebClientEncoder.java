package com.infosoul.mserver.netty.codec;

import com.infosoul.mserver.common.utils.NettyUtils;
import com.infosoul.mserver.dto.netty.FrameDTO;
import com.infosoul.mserver.enums.FrameTypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * client 编码
 *
 * @author xiangyi.long
 * @date 2018-06-20 09:02
 */
public class WebClientEncoder extends MessageToByteEncoder<FrameDTO> {

    @Override
    public void encode(ChannelHandlerContext ctx, FrameDTO msg, ByteBuf out) {
        if (FrameTypeEnum.LOGIN == msg.getFrameType() || FrameTypeEnum.SENSOR_INFO == msg.getFrameType()
                || FrameTypeEnum.SENSOR_DATA == msg.getFrameType()) {
            NettyUtils.stringEncode(msg, out);
        } else if (FrameTypeEnum.HEARTBEAT == msg.getFrameType()) {
            NettyUtils.byteEncode(msg, out);
        } else if (FrameTypeEnum.DEVICE_GEO == msg.getFrameType()) {

        }
    }
}
