package com.mengtu.alogrithm.sort;

public class Main {
    public static void main(String[] args) {
        Sort<Integer> sort = new MergeSort<>();
        Integer[] arr = {1,2,3,5,6,87,235,14,1654,134,1,516,3423,15,56123,5};
        sort.sort(arr);
    }
}
