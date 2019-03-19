package cn.tx.service.impl;

import cn.tx.dao.AccountMapper;
import cn.tx.model.Account;
import cn.tx.service.AccountService;
import cn.tx.service.BloomFilterUtil;
import cn.tx.service.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Autowired
    RedisCacheUtil<Account> redisCacheUtil;

    public List<Account> selectAll() {
        return null;
    }


    public List<Integer> selectAcountIds(){
        return accountMapper.selectAcountIds();
    }

    public Account getAccountById(int accountId) {
        Account account = redisCacheUtil.getCacheObject(accountId+"");
        if(account == null){
            if(BloomFilterUtil.mayContains(accountId)){
                account =  accountMapper.getAccountById(accountId);
                if(account != null)
                    redisCacheUtil.setCacheObject(accountId+"", account, 10);
           }
        }
        return account;
    }

    public void save(Account account){
        accountMapper.save(account);
    }
}
