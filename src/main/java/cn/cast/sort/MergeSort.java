package cn.cast.sort;

@SuppressWarnings("unchecked")
public class MergeSort<T extends Comparable<T>> extends Sort<T>{
    T[] leftArray;
    @Override
    protected void sort() {
        // 准备一段临时的数组空间, 在merge操作中使用
        leftArray = (T[]) new Comparable[array.length >> 1];
        sort(0, array.length);
    }
    protected void sort(int begin,int end){
        if (end - begin < 2) return;
        int mid = (begin + end) >> 1;
        sort(begin,mid);
        sort(mid,end);
        merge(begin,mid,end);
    }

    private void merge(int begin, int mid, int end) {
        //左边起点索引，左边终点索引
        int li = 0, le = mid - begin;
        //右边起点索引，右边终点索引
        int ri = mid;
        int ai = begin;
        //备份左边数组
        for (int i = li; i < le; i++) {
            leftArray[i] = array[begin+i];
        }
        while (li < le){
            if (ri < end && cmpElements(leftArray[li],array[ri]) <= 0){
                array[ai++] = leftArray[li++];
            }else {
                array[ai++] = array[ri++];
            }
        }

    }


}
