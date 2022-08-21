package cn.cast.sort;

public class BubbleSort<E extends Comparable<E>> extends Sort<E>{
    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0 ; end--) {
            for (int begin = 1; begin <= end ; begin++) {
                if (cmp(begin,begin-1) < 0){
                    swap(begin,begin-1);
                }
            }
        }
    }
}
