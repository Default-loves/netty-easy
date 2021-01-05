package com.junyi.netty.pojo;

import com.junyi.netty.entity.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @time: 2021/1/5 11:02
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    /** 经过了 TimeDecoder 后，msg 已经变成了 UnixTime */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        UnixTime m = (UnixTime) msg;
        log.info(m.toString());
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}