package cn.cast.sort;

public class InsertionSort<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            int cur = begin;
            E value = array[cur];
            while (cur > 0 && cmpElements(value,array[cur-1]) < 0) {
                array[cur] = array[cur-1];
                cur--;
            }
            array[cur] = value;
        }
    }

    protected void sort1() {
        for (int begin = 1; begin < array.length; begin++) {
            int search = search(begin);
            for (int i = begin - 1; i >= search; i--) {

            }
        }
    }

    public int search(int index){
        int begin = 0;
        int end = index;
        while (begin < end){
            int mid = (begin + index) >> 1;
            if (cmpElements(array[mid],array[index]) < 0){
                end = mid;
            }else {
                begin = mid+1;
            }
        }
        return begin;
    }
    public int search(int[] array,int v){
        if (array == null || array.length < 1) return -1;
        int begin = 0;
        int end = array.length;
        while (begin < end){
            int mid = (begin + end) >> 1;
            if (v < array[mid]){
                end = mid;
            }else if (v > array[mid]){
                begin = mid + 1;
            }else {
                return mid;
            }
        }
        return -1;
    }
}
