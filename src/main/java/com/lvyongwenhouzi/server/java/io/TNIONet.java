package com.lvyongwenhouzi.server.java.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * <b>测试NIO网络编程</b>
 *
 * @author 11114396 lvyongwen
 * @date 2021-09-10 13:31
 * @since 1.0
 */
public class TNIONet {

    public static class Server {

        public static void main(String[] args) throws IOException {

            // 开启服务端的通道
            ServerSocketChannel ssc = ServerSocketChannel.open();
            // 设置非阻塞
            ssc.configureBlocking(false);

            // 绑定端口
            ssc.bind(new InetSocketAddress(8081));

            // 开启选择器
            Selector selector = Selector.open();
            // 将通道注册到选择器上
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                // 选择已注册的通道
                selector.select();
                // 获取选择通道的事件
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    // 接收
                    if (key.isAcceptable()) {
                        // 从事件中获取通道
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        // 接收连接
                        SocketChannel sc = channel.accept();
                        // 设置非阻塞
                        sc.configureBlocking(false);
                        // 注册读 + 写事件
                        sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    }
                    // 读
                    if (key.isReadable()) {
                        // 获取通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        // 读取数据到buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        sc.read(buffer);
                        // 反转缓冲区
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, buffer.limit()));
                        // 在同一通道上注册,将会将之前注册的事件给注册
                        // 注销read事件
                        sc.register(selector, key.interestOps() ^ SelectionKey.OP_READ);
                    }
                    // 写
                    if (key.isWritable()) {
                        // 获取通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        sc.write(ByteBuffer.wrap("hello client, i am server!".getBytes()));
                        // 注销write事件
                        sc.register(selector, key.interestOps() ^ SelectionKey.OP_WRITE);
                    }
                }
                iterator.remove();
            }
        }

    }


    public static class Client {

        public static void main(String[] args) throws IOException {

            SocketChannel sc = SocketChannel.open();
            sc.connect(new InetSocketAddress(8081));
            sc.write(ByteBuffer.wrap("hello ! i am client !".getBytes()));
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            sc.read(buffer);
            System.out.println(new String(buffer.array(), 0, buffer.position()));

        }
    }

}
