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
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Modification of EchoClient which utilizes Java object serialization.
 */
@Slf4j
public final class ObjectEchoClient {

    static final String HOST ="127.0.0.1";
    static final int PORT = 8007;

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(
                            new ObjectEncoder(),
                            new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                            new ObjectEchoClientHandler());
                }
             });
            // Start the connection attempt.
            ChannelFuture channelFuture = b.connect(HOST, PORT).sync();
            Channel channel = channelFuture.channel();
            if (channelFuture.isSuccess()) {
                System.out.println("start cline");
            }

            while (true) {
                Request request = Request.builder().id(UUID.randomUUID().toString()).msg("ab").build();
                channel.writeAndFlush(request);
                log.info("send message: {}", JSON.toJSONString(request));
                TimeUnit.SECONDS.sleep(10);
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
