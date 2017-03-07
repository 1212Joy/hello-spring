package cn.com.basic.controller;

import cn.com.basic.dto.HelloWorldDto;
import cn.com.basic.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zhaijiayi on 2016/5/3.
 */
@RestController
@RequestMapping("/hi")
public class HelloWorldController {
    @Autowired
    private HelloWorldService helloWorldService;


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String helloWorld1(HelloWorldDto helloWorldDto)  {
        helloWorldService.add(helloWorldDto);
        return helloWorldDto.getName();
    }
    /*@RequestMapping(value = "/queryByList", method = RequestMethod.GET)
    public List<HelloWorldDto> queryByList()  {

        return helloWorldService.queryList();
    }*/

}
