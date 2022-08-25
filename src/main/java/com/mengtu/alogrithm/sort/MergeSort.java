package com.mengtu.alogrithm.sort;

public class MergeSort<E extends Comparable<E>> extends Sort<E>{
    private E[] leftArray;


    @Override
    protected void sort() {
        leftArray = (E[]) new Object[array.length >> 2];
        sort(0,array.length);
    }

    private void sort(int begin, int end) {
        if (end - begin < 2) return;
        int mid = (begin + end) >> 1;
        sort(begin,mid);
        sort(mid,end);
        merge(begin,mid,end);
    }

    private void merge(int begin, int mid, int end) {
        int li = 0;
        int le = mid-begin;
        int ri = mid;
        int ai = begin;

        for (int i = li; i < mid; i++) {
            leftArray[ai++] = array[i];
        }

        while (li < ri){
            if (ri < end && cmp(ri,li) < 0){
                array[ai++] = array[ri++];
            }else {
                array[ai++] = leftArray[li++];
            }
        }

    }


}
