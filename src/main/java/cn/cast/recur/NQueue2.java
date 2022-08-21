package cn.cast.recur;

/**
 *  N皇后问题
 *  回溯经典算法
 * @author 周德永
 * @date 2021/12/9 23:46
 */
public class NQueue2 {

    /*有多少中合理的摆法*/
    static int num = 0;
    /*标记着某一行是否有皇后*/
    static boolean[] cols = null;
    /*标记着某一对角线是否有皇后（左上角->右下角）*/
    static boolean[] leftTop = null;
    /*标记着某一对角线是否有皇后（右上角->左下角）*/
    static boolean[] rightTop = null;

    public static void main(String[] args) {
        int n = 15;
        long l = System.currentTimeMillis();
        nqueue(n);
        long l1 = System.currentTimeMillis();
        System.out.println(num + " cost: "+(l1-l)+" ms"); //12秒
    }


    private static void nqueue(int n) {
        cols = new boolean[n];
        leftTop = new boolean[(n << 1) - 1];
        rightTop = new boolean[leftTop.length];
        place(0);
    }
    public static void place(int row){
        if (row == cols.length){
            num++;
            return;
        }
        for (int col = 0; col < cols.length; col++) {
            if (cols[col]) continue;
            int ltIndex = row - col + cols.length - 1;
            if (leftTop[ltIndex]) continue;
            int rtIndex = row + col;
            if (rightTop[rtIndex]) continue;
            cols[col] = true;
            leftTop[ltIndex] = true;
            rightTop[rtIndex] = true;
            place(row+1);
            cols[col] = false;
            leftTop[ltIndex] = false;
            rightTop[rtIndex] = false;
        }
    }

    static void show(){
//        for (int row = 0; row < cols.length; row++) {
//            for (int col = 0; col < cols.length; col++) {
//                if (cols[row] == col){
//                    System.out.print("1 ");
//                }else {
//                    System.out.print("0 ");
//                }
//            }
//            System.out.println();
//        }
//        System.out.println("-------------------");
    }
}

