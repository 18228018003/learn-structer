package com.mengtu.designpattern.principle.lsp;

public class Test {
    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle();
        rectangle.setLength(20);
        rectangle.setWidth(10);
        resize(rectangle);
        printLengthAndWidth(rectangle);
        System.out.println(" ========== ");
        Square square = new Square();
        square.setLength(10);
        resize(square);
        printLengthAndWidth(square);
    }

    public static void resize(Rectangle rectangle){
        //判断宽如果比长小，进行扩宽的操作
        while (rectangle.getWidth() <= rectangle.getLength()){
            rectangle.setWidth(rectangle.getWidth()+1);
        }
    }

    //打印长和宽
    public static void printLengthAndWidth(Rectangle rectangle){
        System.out.println(rectangle.getLength());
        System.out.println(rectangle.getWidth());
    }
}
