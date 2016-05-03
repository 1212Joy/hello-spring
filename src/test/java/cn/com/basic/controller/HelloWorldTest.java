package cn.com.basic.controller;

import cn.com.basic.BaseTest;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by zhaijiayi on 2016/5/3.
 */
public class HelloWorldTest extends BaseTest {

    @Test
    public void testHelloWorld() throws Exception {
        mockMvc.perform(get("/hi/first")
                .param("first", "hi baby"))
                .andExpect(status().isOk())
                .andDo(new ResultHandler() {
                    @Override
                    public void handle(MvcResult result) throws Exception {
                        String content = result.getResponse().getContentAsString();
                        logger.info("testHelloWorld : {}" ,content);
                    }
                });
    }
}