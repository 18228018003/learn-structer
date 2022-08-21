package cn.cast.bloomfilter;

/**
 *
 *
 * @author 周德永
 * @date 2021/12/17 22:37
 */
public class BloomFilter<T> {

    /*二进制向量的长度（一共有多少个二进制位）*/
    private int bitSize;

    /*二进制向量*/
    private long[] bits;

    /*哈希函数的个数*/
    private int hashSize;


    /**
     * @param n 数据规模
     * @param p 误判率,取值范围（0,1）;
     */
    public BloomFilter(int n, double p){
        if (n <= 0 || p <= 0 || p >= 1){
            throw new IllegalArgumentException("wrong n or p");
        }
        double ln2 = Math.log(2);
        /*求出二进制向量的长度*/
        bitSize = (int) (-( n*Math.log(p)) / (ln2 * ln2));
        /*求出hash函数的个数*/
        hashSize = (int) (bitSize * ln2 / n);
        bits = new long[(bitSize + Long.SIZE - 1) / Long.SIZE];
//        System.out.println(bitSize);
//        System.out.println(hashSize);
    }
    /**
     * 添加元素
     * @param value 值
     */
    public boolean put(T value){
        nullCheck(value);
        /*利用value生成两个整数*/
        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;
        boolean result = false;
        for (int i = 1; i <= hashSize; i++) {
            int combinedHash = hash1 + (i * hash2);
            if (combinedHash < 0){
                combinedHash = ~combinedHash;
            }
            /*生成一个二进制索引*/
            int index = combinedHash % bitSize;
            /*设置index位置的二进制位为1*/
            if (set(index)) result = true;
        }
        return result;
    }

    /**
     * 设置index位置的二进制位为1
     */
    private boolean set(int index) {
        /*找到对应的long位置*/
        long value = bits[index / 64];
        int bitValue = 1 << (index & (Long.SIZE-1));
        /*二进制位在loog内部的索引*/
        bits[index / 64] = value | bitValue;
        return (value & bitValue) == 0;
    }
    /**
     * 查看index位置的二进制位的值
     */
    private boolean get(int index) {
        /*找到对应的long位置*/
        long value = bits[index / 64];
        /*二进制位在loog内部的索引   false=0,true=1*/
        return (value & (1L << (index & (Long.SIZE - 1)))) != 0;
    }

    private void nullCheck(T value) {
        if (value==null){
            throw new RuntimeException("value can not be null");
        }
    }

    /**
     * 查询一个元素是否存在
     */
    public boolean contains(T value){
        nullCheck(value);
        /*利用value生成两个整数*/
        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;

        for (int i = 1; i <= hashSize; i++) {
            int combinedHash = hash1 + (i * hash2);
            if (combinedHash < 0){
                combinedHash = ~combinedHash;
            }
            /*生成一个二进制索引*/
            int index = combinedHash % bitSize;
            /*查看index位置的二进制位是否为0*/
            /*为0 说明这个值一定不存在此hashMap中*/
            if (!get(index)) return false;
        }
        return true;
    }
}
