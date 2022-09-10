package com.mengtu.letcode.stack;

import java.util.Stack;

public class _739_Temperatures {
    public int[] dailyTemperatures(int[] temperatures) {
        int[] ris = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < temperatures.length; i++){
            while(!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]){
                int topIdx = stack.peek();
                ris[topIdx] = i - topIdx;
                stack.pop();
            }
            stack.push(i);
        }
        return ris;
    }

    public int[] dailyTemperatures1(int[] temperatures) {
        int[] ris = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < temperatures.length; i++){
            while(!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]){
                ris[stack.pop()] = i;
            }
            stack.push(i);
        }
        int[] res = new int[temperatures.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = Math.max(ris[i] - i,0);
        }
        return res;
    }

    /**
     * 倒推法
     * @param temperatures
     * @return
     */
    public int[] dailyTemperaturesDy(int[] temperatures) {
        if (temperatures == null || temperatures.length == 0) return null;
        int[] values = new int[temperatures.length];

        for (int i = temperatures.length - 2; i >= 0; i--) {
            int j = i + 1;
            while (true){
                if (temperatures[i] < temperatures[j]){
                    values[i] = j - i;
                    break;
                }else if (values[j] == 0){
                    values[i] = 0;
                    break;
                }else if (temperatures[i] == temperatures[j]){
                    values[i] = values[j] + j - i;
                    break;
                }else {
                    j = j + values[j];
                }
            }
        }
        return values;
    }
}
