package com.cepheis.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeServer {

    public static void main(String[] args) throws Exception {
        new TimeServer().bind(8086);
    }

    public void bind(int port) throws Exception {
        //配置服务端的nio线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChildChannelHandler());
            //绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();
            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            {
                //释放线程池资源
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel channel) {
            // channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("\n".getBytes())));
            channel.pipeline().addLast(new FixedLengthFrameDecoder(17));
            channel.pipeline().addLast(new StringDecoder());
            // channel.pipeline().addLast(new TimeServerHandler());
            channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {

//                @Override
//                public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//
//                }
//
//                @Override
//                public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//
//                }
//
//                @Override
//                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//
//                }
//
//                @Override
//                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                    System.out.println(msg);
//                    byte[] result = ("server:" + msg + System.getProperty("line.separator")).getBytes();
//                    ByteBuf buf = Unpooled.copiedBuffer(result);
//                    ctx.writeAndFlush(buf);
//                }
//
//                @Override
//                public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//                    ctx.flush();
//                }
//
//                @Override
//                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//                    cause.printStackTrace();
//                    ctx.close();
//                }
            });
        }
    }
}