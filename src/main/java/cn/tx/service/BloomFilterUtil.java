package cn.tx.service;

import cn.tx.dao.AccountMapper;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;


public class BloomFilterUtil {

    //@Autowired
    private AccountMapper accountMapper;

    public void setAccountMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    private static final int capacity = 1000000;

    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), capacity);

    public static boolean mayContains(Integer i){
        return bloomFilter.mightContain(i);
    }

    public void init(){
        List<Integer> integers = accountMapper.selectAcountIds();
        for (Integer i : integers){
            bloomFilter.put(i);
        }

    }

}
