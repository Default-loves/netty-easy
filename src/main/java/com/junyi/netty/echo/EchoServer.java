package com.junyi.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 将客户端发送的消息原样返回给客户端
 * 两种发送方式，任选其一即可：
 * 1. 执行 EchoClient
 * 2. telnet 172.16.40.106 12356
 * @time: 2021/1/5 9:40
 * @author: junyi Xu
 */
public class EchoServer {

    private static final int port = 12356;

    public void run(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // boss accepts an incoming connection
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // work handles the traffic of the accepted connection once the boss accepts the connection and registers the accepted connection to the worker.
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)          // option() is for the NioServerSocketChannel that accepts incoming connections.
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // childOption() is for the Channels accepted by the parent ServerChannel, in this cases, the parent is NioServerSocketChannel
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });

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
        new EchoServer().run(port);
    }


}
