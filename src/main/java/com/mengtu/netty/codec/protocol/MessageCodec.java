package com.mengtu.netty.codec.protocol;

import com.mengtu.netty.codec.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        //1.4字节魔数
        out.writeBytes(new byte[]{'c','a','f','e'});
        //2.1字节版本
        out.writeByte(1);
        //3.1字节序列化方式 0 jdk; 1 json
        out.writeByte(0);
        //4.1字节指令类型
        out.writeByte(msg.getMessageType());
        //5.4字节请求序号
        out.writeByte(msg.getSequenceId());
        //对齐填充用  15 字节变为 16 字节
        out.writeByte(0xff);
        //6.获取内容字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] bytes = bos.toByteArray();
        //7.正文长度
        out.writeInt(bytes.length);
        //8.写入内容
        out.writeBytes(bytes);

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //魔数 4
        int magicNum = in.readInt();
        //版本 1
        byte version = in.readByte();
        // 1
        byte serializerType = in.readByte();
        // 1
        byte messageType = in.readByte();
        // 1
        int sequenceId = in.readByte();
        in.readByte();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes,0,length);
        Message message = null;
        if (serializerType == 0){
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            message = (Message) ois.readObject();
        }
//        log.debug("{},{},{},{},{},{}",magicNum,version,serializerType,messageType,sequenceId,length);
//        log.debug("{}",message);
        out.add(message);
    }
}
