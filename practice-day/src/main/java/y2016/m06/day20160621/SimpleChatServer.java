package y2016.m06.day20160621;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-06-21 PM03:59
 */
public class SimpleChatServer {
    private final int port;

    public SimpleChatServer(int port) {
        this.port = port;
    }

    private void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new SimpleChatServerInitializer());

            ChannelFuture future = bootstrap.bind(port).sync();

            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }


    public static void main(String[] args) {
        try {
            SimpleChatServer timeServer = new SimpleChatServer(9090);
            timeServer.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
