package cn.com.basic.service;

import cn.com.basic.config.ApplicationConfiguration;
import cn.com.basic.dao.HelloWorldDao;
import cn.com.basic.dto.HelloWorldDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * @param params
     * @return
     */
  /*  @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    public List<HelloWorldDto>  queryList(String  ids){
        String[] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<Integer>();
        for(int i=0;i<idArray.length;i++){
            idList.add(Integer.parseInt(idArray[i]));
        }

        return  helloWorldDao.queryList(idList);
    }*/
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    public List<HelloWorldDto>  queryList(Map<String, Object> params){

        return  helloWorldDao.queryList(params);
    }

    @Transactional(value = ApplicationConfiguration.TRANSACTION_MANAGER, propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED, readOnly = false, rollbackFor = Exception.class)
    public Integer updateList(List<HelloWorldDto> list){

        return  helloWorldDao.updateList(list);
    }

    @Transactional(value = ApplicationConfiguration.TRANSACTION_MANAGER, propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED, readOnly = false, rollbackFor = Exception.class)
    public void updateListTem(List<HelloWorldDto> list){
        list.forEach(helloWorldDto -> {
            helloWorldDao.update(helloWorldDto);
        });

    }
}

