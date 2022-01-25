/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.junyi.netty.objectecho;

import com.alibaba.fastjson.JSON;
import com.junyi.netty.entity.Request;
import com.junyi.netty.entity.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 */
@Slf4j
public class ObjectEchoServerHandler extends SimpleChannelInboundHandler<Request> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("hello");
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {
        log.info("receive message: {}", JSON.toJSONString(msg));
        Response response = Response.builder().code(200).message("OK").build();
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("error: ", cause);
    }
}
