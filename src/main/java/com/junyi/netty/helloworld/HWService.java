package com.junyi.netty.helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @time: 2021年11月25日 14:30
 * @author: junyi Xu
 */
public class HWService {

    private static final int port = 12356;

    public void run(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // boss accepts an incoming connection
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // work handles the traffic of the accepted connection once the boss accepts the connection and registers the accepted connection to the worker.
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                            // the encoder and decoder are static as these are sharable
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            // and then business logic.
                            pipeline.addLast(new ServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // option() is for the NioServerSocketChannel that accepts incoming connections.
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // childOption() is for the Channels accepted by the parent ServerChannel, in this cases, the parent is NioServerSocketChannel

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new HWService().run(port);
    }
}
