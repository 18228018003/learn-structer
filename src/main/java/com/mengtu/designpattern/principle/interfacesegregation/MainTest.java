package com.mengtu.designpattern.principle.interfacesegregation;

public class MainTest {
    public static void main(String[] args) {
        HeimaSafetyDoor door = new HeimaSafetyDoor();
        door.antiTheft();
        door.fireProof();
        door.waterProof();
    }
}
