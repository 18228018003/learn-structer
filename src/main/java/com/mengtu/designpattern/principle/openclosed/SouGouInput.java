package com.mengtu.designpattern.principle.openclosed;

public class SouGouInput {
    private AbstractSkin skin;

    public void setSkin(AbstractSkin skin){
        this.skin = skin;
    }

    public void disPlay(){
        skin.disPlay();
    }

}
