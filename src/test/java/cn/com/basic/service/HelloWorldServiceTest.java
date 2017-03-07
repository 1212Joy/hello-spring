package cn.com.basic.service;

import cn.com.basic.BaseTest;
import cn.com.basic.dto.HelloWorldDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by ZhaiJiaYi on 2017/2/22.
 */

public class HelloWorldServiceTest extends BaseTest{

    @Autowired
    HelloWorldService helloWorldService;

    @Test
    public void testQuery() throws Exception {
        Map<String, Object> params = new HashMap();
        List<Integer> idList = new ArrayList<Integer>();
        idList.add(1);
        idList.add(2);
        params.put("ids", idList);
        params.put("name", "aaa");

        helloWorldService.queryList(params);
    }

    @Test
    public void testUpdateList() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<HelloWorldDto> list = new ArrayList();

        HelloWorldDto h1 = new HelloWorldDto();
        h1.setId(7);
        h1.setName("fff");
        list.add(h1);
        HelloWorldDto h2 = new HelloWorldDto();
        h2.setId(8);
        h2.setName("rrr");
        list.add(h2);
         helloWorldService.updateList(list);

      System.out.println("---- : ");
    }

    @Test
    public void testUpdateListTem() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<HelloWorldDto> list = new ArrayList();

        HelloWorldDto h1 = new HelloWorldDto();
        h1.setId(7);
        h1.setName("eee");
        list.add(h1);
        HelloWorldDto h2 = new HelloWorldDto();
        h2.setId(8);
        h2.setName("www");
        list.add(h2);
         helloWorldService.updateListTem(list);

        System.out.println("---- : ");
    }
}