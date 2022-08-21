package com.mengtu.structer.heap;

public class MainTest {
    public static void main(String[] args) {

       Integer[] nums = {83, 62, 77, 97, 43, 91, 52, 22, 5, 70,
               84, 41, 95, 63, 48, 98, 82, 36, 16, 89, 88,
               99, 4, 74, 53, 59, 92, 34, 33, 93, 46, 81,
               37, 12, 64, 69, 94, 100, 38, 21, 54, 1, 9,
               67, 85, 58, 15, 51, 56, 57, 3, 24, 13, 14,
               71, 49, 45, 66, 25, 50, 28, 17, 20, 31, 10,
               8, 72, 18, 32, 19, 79, 61, 87, 7, 78, 40,
               11, 65, 90, 80, 73, 68, 75, 42, 39, 55,
               86, 30, 23, 96, 47};
        BinaryHeap<Integer> heap = new BinaryHeap<>((o1, o2) -> o2-o1);
        for (int i = 0; i < nums.length; i++) {
            if (i < 5){
                heap.add(nums[i]);
            }else if (nums[i] > heap.get()){
                heap.replace(nums[i]);
            }
        }

        while (!heap.isEmpty()){
            System.out.println(heap.remove());
        }
    }
}
