package com.mengtu.net.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static com.mengtu.utils.ByteBufferUtil.debugAll;

public class Server {
    public static void split(ByteBuffer source){
        source.flip();//切换为读模式
        int limit = source.limit();
        for (int i = 0; i < limit; i++) {
            if (source.get(i) == '\n'){
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
        source.compact();//切换为写模式

    }
    public static void main(String[] args) throws IOException {
        //1 创建Selector 管理多个channel
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        //2 建立Selector和channel之间的联系
        // selectionKey 将来事件发生时，通过它可以知道和那个channel发生的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        //key 只关注accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8888));
        while (true){
            //3.selector的select方法
            selector.select();//没有事件发生，阻塞
            //4.处理时间
            //内部包含了所有发生了的事件 主要是为了遍历的时候删除 才使用迭代器
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();//如果没有此代码，36行会报空指针。
                //5.区分事件类型
                if (key.isAcceptable()) { //如果是accept事件
                    ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    //不能是局部变量 每个socketChannel拥有自己独有的ByteBuffer
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    //将ByteBuffer作为附件关联到selectionKey上
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                }else if (key.isReadable()){//如果是read事件
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();//拿到触发事件的channel
                        //获取selectionKey上关联的附件
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int read = channel.read(buffer);
                        if (read == -1){
                            key.cancel();
                        }else {
                            split(buffer);
                            if (buffer.position() == buffer.limit()){
                                ByteBuffer byteBuffer = ByteBuffer.allocate(buffer.capacity() << 1);
                                buffer.flip();
                                byteBuffer.put(buffer);
                                key.attach(byteBuffer);
                            }
                        }
                    }catch (IOException e){
                        key.cancel();
                    }
                }
            }
        }
    }
}
