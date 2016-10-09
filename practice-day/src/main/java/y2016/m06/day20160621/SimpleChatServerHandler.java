package y2016.m06.day20160621;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-06-21 PM04:20
 */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> {
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for(Channel channel : channelGroup) {
            channel.writeAndFlush("[Server - ]" + incoming.remoteAddress() + "移除\n");
        }
        channelGroup.remove(ctx.channel());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for(Channel channel : channelGroup) {
            channel.writeAndFlush("[Server - ]" + incoming.remoteAddress() + "加入\n");
        }
        channelGroup.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel inactiveChannel = ctx.channel();
        System.out.println("SimpleChatClient:" + inactiveChannel.remoteAddress() + "离线\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel activeChannel = ctx.channel();
        System.out.println("SimpleChatClient:" + activeChannel.remoteAddress() + "在线\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "异常\n");
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        Channel incoming = ctx.channel();
        for(Channel channel : channelGroup) {
            if(channel == incoming) {
                channel.writeAndFlush("[YOU]" + s + "\n");
            } else {
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + s + "\n");
            }
        }
    }
}
