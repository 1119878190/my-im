package com.myim.utils;



import com.alibaba.fastjson2.JSONObject;
import com.myim.proto.Message;
import com.myim.proto.MessageHeader;
import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 字节转消息实体工具类
 *
 * @author lx
 * @date 2023/05/01
 * @description: 将ByteBuf转化为Message实体，根据私有协议转换
 * 私有协议规则，
 * 4位表示Command表示消息的开始，
 * 4位表示version
 * 4位表示clientType
 * 4位表示messageType
 * 4位表示appId
 * 4位表示imei长度
 * imei
 * 4位表示数据长度
 * data
 * 后续将解码方式加到数据头根据不同的解码方式解码，如pb，json，现在用json字符串
 */


public class ByteBufToMessageUtils {


    /**
     * 转换
     *
     * @param in byteBuf
     * @return {@link Message}
     */
    public static Message transition(ByteBuf in) {


        /** 获取command*/
        int command = in.readInt();

        /** 获取version*/
        int version = in.readInt();

        /** 获取clientType*/
        int clientType = in.readInt();

        /** 获取clientType*/
        int messageType = in.readInt();

        /** 获取appId*/
        int appId = in.readInt();

        /** 获取imeiLength*/
        int imeiLength = in.readInt();

        /** 获取bodyLen*/
        int bodyLen = in.readInt();


        // 如果 剩余可读字节长度小于 imeiLength + bodyLen  说明不是一个完整的包，重置 rid读索引
        if (in.readableBytes() < imeiLength + bodyLen) {
            in.resetReaderIndex();
            return null;
        }

        byte[] imeiData = new byte[imeiLength];
        in.readBytes(imeiData);
        String imei = new String(imeiData);

        byte[] bodyData = new byte[bodyLen];
        in.readBytes(bodyData);


        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setAppId(appId);
        messageHeader.setClientType(clientType);
        messageHeader.setCommand(command);
        messageHeader.setLength(bodyLen);
        messageHeader.setVersion(version);
        messageHeader.setMessageType(messageType);
        messageHeader.setImei(imei);

        Message message = new Message();
        message.setMessageHeader(messageHeader);

        if (messageType == 1) {
            String body = new String(bodyData, StandardCharsets.UTF_8);
            JSONObject parse = (JSONObject) JSONObject.parse(body);
            message.setMessagePack(parse);
        }

        // 标记读索引
        in.markReaderIndex();
        return message;

    }

}
