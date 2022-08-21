package cn.cast.recur;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  N皇后问题
 *  回溯经典算法
 * @author 周德永
 * @date 2021/12/9 23:46
 */
public class NQueue {
    static int num = 0;
    static int[] cols = null;

    public List<String> letterCombinations(String digits) {
        List<String> combinations = new ArrayList<>();
        if (digits.length() == 0) {
            return combinations;
        }
        return combinations;
    }

    public void backtrack(List<String> combinations, Map<Character, String> phoneMap, String digits, int index, StringBuffer combination) {
        if (index == digits.length()) {
            combinations.add(combination.toString());
        } else {
            char digit = digits.charAt(index);
            String letters = phoneMap.get(digit);
            int lettersCount = letters.length();
            for (int i = 0; i < lettersCount; i++) {
                combination.append(letters.charAt(i));
                backtrack(combinations, phoneMap, digits, index + 1, combination);
                combination.deleteCharAt(index);
            }
        }
    }
    public static void main(String[] args) {
        int n = 4;
        long l = System.currentTimeMillis();
        char[][] board = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        long l1 = System.currentTimeMillis();
        System.out.println(num + " cost: "+(l1-l)+" ms"); //28秒
    }

    public boolean exist(char[][] board, String word) {
        char[] words = word.toCharArray();
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(back(board,words,i,j,0)) return true;
            }
        }
        return false;
    }

    public boolean back(char[][] board,char[] words,int i,int j,int k){

        if(i >= board.length || i < 0 || j >= board[0].length || j < 0 || k >= words.length || board[i][j] != words[k]) return false;
        board[i][j] = '\0';
        if (k == words.length-1) return true;
        /* if (k == words.length - 1)*/
        boolean res = back(board, words, i+1, j, k+1) || back(board, words, i-1, j, k+1)
                || back(board,words,i,j+1,k+1) || back(board,words,i,j-1,k+1);
        board[i][j] = words[k];
        return res;
    }
    public static void nqueu1e(int n){
        cols = new int[n];
        palc(0);
    }

    private static void palc(int row) {
        if (row == cols.length) {
            num++;
            return;
        }
        for (int col = 0; col < cols.length; col++) {
            if (idvalisd(row,col)){
                cols[row] = col;
                palc(row+1);
            }
        }
    }
    private static boolean idvalisd(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (cols[i] == col) return false;
            if (row - i == Math.abs(cols[i]-col)) return false;
        }
        return true;
    }




    private static void nqueue(int n) {
        cols = new int[n];
        place(0);
    }
    public static void place(int row){
        if (row == cols.length){
            num++;
            show();
            return;
        }
        for (int col = 0; col < cols.length; col++) {
            if (isValid(row,col)){
                cols[row] = col;
                place(row+1);
            }
        }
    }

    private static boolean isValid(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (cols[i] == col) return false;
            if ((row - i == Math.abs(cols[i] - col))) {
                return false;
            }
        }
        return true;
    }

    static void show(){
        for (int i : cols) {
            for (int col = 0; col < cols.length; col++) {
                if (i == col) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        System.out.println("-------------------");
    }
}

