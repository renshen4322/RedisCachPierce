package cn.tx.service;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class BloomFilterTest {


    public static final int capacity = 1000000;
    public static final int key = 999998;

    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), capacity);


    static {
        for (int i = 0; i < capacity; i++) {
            bloomFilter.put(i);

        }
    }


    public static void main(String[] args) {
        long start = System.nanoTime();
        boolean b = bloomFilter.mightContain(key);
        if(b){
            System.out.println("成功获得到： "+key);
        }
        long end = System.nanoTime();
        System.out.println("布隆过滤器的时间是："+(end - start));


        int sum = 0;
        for (int i = capacity+ 10000; i <= capacity+20000 ; i++) {
            if(bloomFilter.mightContain(i)){
                sum++;
            }
        }

        System.out.println("错判个数："+sum);

    }
}
