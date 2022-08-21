package cn.cast.dp;

public class CardsInLine {
    public static void main(String[] args) {
        int[] arr = {5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7};
        long l = System.currentTimeMillis();
        System.out.print(win(arr) + ":");
        long l2 = System.currentTimeMillis();
        System.out.println((l2 - l) + " ms");
        System.out.print(win2(arr)+ ":"  );
        System.out.println((System.currentTimeMillis() - l2) + " ms");
    }

    //根据规则返回获胜者的分数
    public static int win3(int[] arr){
        int n = arr.length;
        int[][] fmap = new int[n][n];
        int[][] gmap = new int[n][n];
        for (int i = 0; i < n; i++) {
            fmap[i][i] = arr[i];
        }
        for (int startCol = 1; startCol < n; startCol++) {
            int L = 0;
            int R = startCol;
            while (R < n){
                fmap[L][R] = Math.max(arr[L] +gmap[L+1][R],arr[R]+gmap[L][R-1]);
                gmap[L][R] = Math.min(fmap[L+1][R],fmap[L][R]-1);
                L++;
                R++;
            }
        }
        return Math.max(fmap[0][n-1],gmap[0][n-1]);
    }
    //根据规则返回获胜者的分数
    public static int win2(int[] arr){
        int n = arr.length;
        int[][] fmap = new int[n][n];
        int[][] gmap = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                fmap[i][j] = -1;
                gmap[i][j] = -1;
            }
        }
        int first = f1(arr,0,n-1,fmap,gmap);
        int second = g1(arr,0,n-1,fmap,gmap);
        return Math.max(first,second);
    }

    //arr[l...r],先手获得最好分数返回
    public static int f1(int[] arr,int l,int r,int[][] fmap,int[][] gmap){
        if (fmap[l][r] != -1){
            return fmap[l][r];
        }else if (l == r){
            fmap[l][r] = arr[l];
        }else{
            int p1 = arr[l] + g1(arr,l+1,r,fmap,gmap); //先手选择l位置上的牌，只能作为后手选[l+1,r]
            int p2 = arr[r] + g1(arr,l,r-1,fmap,gmap); //先手选择r位置上的牌，只能作为后手选[l,r-1]
            fmap[l][r] = Math.max(p1,p2);
        }
        return fmap[l][r];
    }
    //arr[l...r],后手获得最好分数返回
    private static int g1(int[] arr, int l, int r,int[][] fmap, int[][] gmap) {
        if (gmap[l][r] != -1){
            return gmap[l][r];
        }else if (l == r){
            gmap[l][r] =  0;
        }else {
            int p1 = f1(arr,l+1,r,fmap,gmap); //对手拿走了L位置的数
            int p2 = f1(arr,l,r-1,fmap,gmap); //对手拿走了R位置的数
            gmap[l][r] = Math.min(p1,p2);
        }
        return gmap[l][r];
    }


    //根据规则返回获胜者的分数
    public static int win(int[] arr){
        int first = f(arr,0,arr.length-1);
        int second = g(arr,0,arr.length-1);
        return Math.max(first,second);
    }

    //arr[l...r],先手获得最好分数返回
    public static int f(int[] arr,int l,int r){
        if (l == r){
            return arr[l];
        }
        int p1 = arr[l] + g(arr,l+1,r); //先手选择l位置上的牌，只能作为后手选[l+1,r]
        int p2 = arr[r] + g(arr,l,r-1); //先手选择r位置上的牌，只能作为后手选[l,r-1]
        return Math.max(p1,p2);
    }
    //arr[l...r],后手获得最好分数返回
    private static int g(int[] arr, int l, int r) {
        if (l == r){
            return 0;
        }
        int p1 = f(arr,l+1,r); //对手拿走了L位置的数
        int p2 = f(arr,l,r-1); //对手拿走了R位置的数
        return Math.min(p1,p2);
    }
}
