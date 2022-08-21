package com.zdy.learn.tanxin;

import java.util.Arrays;
import java.util.Comparator;

/**
 *  贪心算法求会议安排时间
 * @author 周德永
 * @date 2021/10/31 21:45
 */
public class BestArrange {
    public static class Program{
        public int start;
        public int end;

        public Program(int start,int end){
            this.start = start;
            this.end = end;
        }

    }
    public static class ProgramComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }

    }
    public static int bestArrange(Program[] programs,int timePoint){
        Arrays.sort(programs,new ProgramComparator());
        int result = 0;

        /*从左往右依次遍历所有会议*/
        for (Program program : programs) {
            if (timePoint <= program.start) {
                result++;
                timePoint = program.end;
            }

        }
        return result;
    }
}
