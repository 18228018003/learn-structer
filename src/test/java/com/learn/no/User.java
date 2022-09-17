package com.learn.no;

import org.junit.Test;

import java.util.Objects;

public class User {
    private String account;
    private String pwd;
    private int age;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(account, user.account) && Objects.equals(pwd, user.pwd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, pwd, age);
    }
}
class UserOperation{
    public boolean userInArr(User[] users,User user){
        for (User u : users) {
            if (u.equals(user)){
                return true;
            }
        }
        return false;
    }
    @Test
    public void testUser(){
        User[] users = new User[10];
        for (int i = 0; i < users.length; i++) {
            User user = new User();
            user.setAccount(" 第 "+i + " 个用户");
            user.setPwd(i+"123123");
            user.setAge(24);
            users[i] = user;
        }

    }
}