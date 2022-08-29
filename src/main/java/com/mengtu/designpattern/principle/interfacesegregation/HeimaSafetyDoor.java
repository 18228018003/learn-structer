package com.mengtu.designpattern.principle.interfacesegregation;

public class HeimaSafetyDoor implements AntiTheft,FireProof,WaterProof{
    @Override
    public void antiTheft() {
        System.out.println("黑马 防盗");
    }

    @Override
    public void fireProof() {
        System.out.println("黑马防火");
    }

    @Override
    public void waterProof() {
        System.out.println("黑马防水");
    }
}
