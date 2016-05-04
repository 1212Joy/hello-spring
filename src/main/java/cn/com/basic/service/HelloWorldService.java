package cn.com.basic.service;

import cn.com.basic.config.ApplicationConfiguration;
import cn.com.basic.dao.HelloWorldDao;
import cn.com.basic.dto.HelloWorldDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhaijiayi on 2016/5/3.
 */
@Service
public class HelloWorldService {
    @Autowired
    private HelloWorldDao helloWorldDao;

    @Transactional(value = ApplicationConfiguration.TRANSACTION_MANAGER, propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED, readOnly = false, rollbackFor = Exception.class)
    public  void add(HelloWorldDto helloWorldDto){

        helloWorldDao.add(helloWorldDto);
    }
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    public  HelloWorldDto query(Integer id){

       return  helloWorldDao.query(id);
    }
}

