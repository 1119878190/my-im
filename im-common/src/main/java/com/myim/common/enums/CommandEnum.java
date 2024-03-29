package com.myim.common.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author lx
 * @date 2024/03/29
 */
@Getter
public enum CommandEnum {


    LOGIN(10000, "登录"),

    SEND_SINGLE_MESSAGE(10001, "发送单聊消息");


    private final Integer command;

    private final String desc;

    CommandEnum(Integer command, String desc) {
        this.command = command;
        this.desc = desc;
    }


    public static CommandEnum of(Integer command) {
        if (Objects.isNull(command)) {
            return null;
        }

        CommandEnum[] values = values();
        for (CommandEnum value : values) {
            if (value.getCommand().equals(command)) {
                return value;
            }
        }
        return null;
    }

}
