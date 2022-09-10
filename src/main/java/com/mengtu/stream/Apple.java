package com.mengtu.stream;

import java.util.Objects;

public class Apple {
    private String color;
    private int id;
    private String address;

    public Apple(int id, String color, String address) {
        this.color = color;
        this.id = id;
        this.address = address;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apple apple = (Apple) o;
        return id == apple.id && Objects.equals(color, apple.color) && Objects.equals(address, apple.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, id, address);
    }

    @Override
    public String toString() {
        return "Apple{" +
                "color='" + color + '\'' +
                ", id=" + id +
                ", address='" + address + '\'' +
                '}';
    }
}
