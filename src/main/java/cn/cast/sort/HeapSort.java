package cn.cast.sort;

public class HeapSort<T extends Comparable<T>> extends Sort<T>{
    private int heapSize;

    @Override
    protected void sort() {
        //原地建堆
        heapSize = array.length;
        for (int i = (heapSize - 1); i >= 0 ; i--) {
            siftDown(i);
        }
        while (heapSize > 1){
            //交换堆顶元素和尾部元素
            swap(0,--heapSize);
            //恢复堆的性质
            siftDown(0);
        }
    }

    private void siftDown(int index) {
        Integer element = (Integer) array[index];
        int half = heapSize >> 1;
        while (index < half){
            int childIndex = (index << 1) + 1;
            int rightIndex = childIndex + 1;
            Integer child = (Integer) array[childIndex];
            if (rightIndex < heapSize && cmpElements(array[rightIndex], (T) child) > 0){
                child = (Integer) array[childIndex = rightIndex];
            }
            if (element >= child) break;
            array[index] = (T) child;
            index = childIndex;
        }
        array[index] = (T) element;
    }
    private void siftUp(int index) {
        Integer element = (Integer) array[index];
        while (index > 0){
            int pIndex = (index-1) >> 1;
            Integer parent = (Integer) array[pIndex];
            if (element <= parent) break;
            array[index] = (T) parent;
            index = pIndex;
        }
        siftUp(1);
        array[index] = (T) element;
    }


}
