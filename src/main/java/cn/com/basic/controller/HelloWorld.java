package cn.com.basic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhaijiayi on 2016/5/3.
 */
@RestController
@RequestMapping("/hi")
public class HelloWorld {
    @RequestMapping(value = "/first", method = RequestMethod.GET)
    public String helloWorld(String first)  {

        return first;
    }
}
