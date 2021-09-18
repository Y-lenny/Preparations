package com.lvyongwenhouzi.devops.services.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;


/**
 * <b>NIO 服务器</b>
 *
 * @author 11114396 lvyongwen
 * @date 2021-09-14 20:02
 * @since 1.0
 */
public class EchoServer {

    private static int PORT = 8081;

    public static void main(String[] args) throws InterruptedException {

        // 指定mainReactor：专门用于接受客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 指定subReactor：专门用于处理IO事件（1、reader事件，2、writer事件）
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // 用户自定义的ThreadPool
        EventExecutorGroup threadPool = new UnorderedThreadPoolEventExecutor(1);
        try {
            // Server启动器
            ServerBootstrap b = new ServerBootstrap();
            // 指定一/二个Reactor
            b.group(bossGroup, workerGroup)
                    // 指定一个Channel工厂，本例中该工厂生产服务端用于accept客户端连接的Channel，将默认使用Channel的无参构造方法。
                    .channel(NioServerSocketChannel.class)
                    // 指定TCP相关的参数以及一些Netty自定义的参数。
                    .option(ChannelOption.SO_BACKLOG, 100)
                    // .handler() 指定mainReactor的处理器，只是默认情况下mainReactor中已经添加了acceptor处理器，所以无需再指定。
                    //指定subReactor中的处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            // 不在addLast(Handler)方法中指定线程池，那么将使用默认的subReacor即woker线程池也即IO线程池执行处理器中的业务逻辑代码。
                            p.addLast(threadPool,
                                    // 解码处理器
                                    new DecoderHandler(),
                                    // 计算处理器
                                    new ComputeHandler(),
                                    // 编码处理器
                                    new EncoderHandler());
                        }
                    });
            // 绑定到本地端口等待客户端连接
            ChannelFuture f = b.bind(PORT).sync();
            // 等待接受客户端连接的Channel被关闭
            f.channel().closeFuture().sync();
        } finally {
            // 关闭两个线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            threadPool.shutdown();
        }
    }
}
