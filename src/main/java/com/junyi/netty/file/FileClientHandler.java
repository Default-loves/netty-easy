package com.junyi.netty.file;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @time: 2021年12月02日 17:51
 * @author: junyi Xu
 */
@Slf4j
public class FileClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("receive: {}", JSON.toJSONString(msg));
        super.channelRead(ctx, msg);
    }
}
