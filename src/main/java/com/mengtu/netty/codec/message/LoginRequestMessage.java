package com.mengtu.netty.codec.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
public class LoginRequestMessage extends Message {
    private String username;
    private String password;

    @Override
    public int getMessageType() {
        return LoginRequestMessage;
    }
}
