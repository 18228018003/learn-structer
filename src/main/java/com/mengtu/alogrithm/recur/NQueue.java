package com.mengtu.alogrithm.recur;

/**
 * N皇后问题    递归 回溯
 */
public class NQueue {

    /**
     * 数据索引是行号,数组元素是列号
     * 如: cols[4] = 5 表示 第四行的皇后在第五列
     */
    int[] cols;

    /**
     * 一种有多少种摆法
     */
    int ways;

    /**
     * 摆放皇后
     */
    void placeQueue(int n){
        if (n < 1) return;
        cols = new int[n];
        place(0);
        System.out.println("ways: " + ways);
    }

    /**
     * 从 row行开始摆放皇后
     * @param row 行号
     */
    private void place(int row) {
        //
        if (row == cols.length){
            ways++;
            show();
            return;
        }

        for (int i = 0; i < cols.length; i++) {
            if (isValid(row,i)){ //剪枝操作，不符合条件的剔除
                //在第row行第i列摆放皇后
                cols[row] = i;
                place(row + 1);
            }
        }
    }

    /**
     * 判断第row行 第i列是否可以摆放皇后
     * @param row 行
     * @param col 列
     * @return 放回能否摆放皇后
     */
    private boolean isValid(int row, int col) {
        //查看当前行之前所有摆放的皇后
        for (int i = 0; i < row; i++) {
            if (cols[i] == col) return false;
            if (row - i  == Math.abs(cols[i] - col)) return false;
        }
        return true;
    }
    void show(){
        for (int row = 0; row < cols.length; row++) {
            for (int col = 0; col < cols.length; col++) {
                if (cols[row]  == col){
                    System.out.print(1 + " ");
                }else {
                    System.out.print(0 + " ");
                }
            }
            System.out.println();
        }
        System.out.println("------------------");
    }
    public static void main(String[] args) {
        NQueue queue = new NQueue();
        queue.placeQueue(4);
//        queue.placeQueue(6);
//        queue.placeQueue(8);
    }
}
