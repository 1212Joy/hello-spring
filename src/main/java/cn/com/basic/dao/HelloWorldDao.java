package cn.com.basic.dao;

import cn.com.basic.dto.HelloWorldDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaijiayi on 2016/5/3.
 */
public interface HelloWorldDao {
    //add
    void add(HelloWorldDto helloWorldDto);
    //delete
    void delete(Integer id);
    //update
    void update(HelloWorldDto helloWorldDto);
    //query
    HelloWorldDto query(Integer id);

    List<HelloWorldDto> getAll();

 //   List<HelloWorldDto> queryList(@Param("item") List<Integer> ids);

    List<HelloWorldDto> queryList(Map<String, Object> params);

    Integer updateList(List<HelloWorldDto> params);

    Integer update(List<HelloWorldDto> params);
}
