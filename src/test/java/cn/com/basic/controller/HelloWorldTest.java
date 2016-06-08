package cn.com.basic.controller;

import cn.com.basic.BaseTest;
import cn.com.basic.dto.HelloWorldDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by zhaijiayi on 2016/5/3.
 */
public class HelloWorldTest extends BaseTest {
    @Autowired()
    HelloWorldDto elloWorldDto;

    @Test
    public void testHelloWorld1() throws Exception {
        mockMvc.perform(get("/hi/first")
                .param("name", "hi baby"))
                .andExpect(status().isOk())
                .andDo(new ResultHandler() {
                    @Override
                    public void handle(MvcResult result) throws Exception {
                        String content = result.getResponse().getContentAsString();
                        logger.info("testHelloWorld : {}" ,content);
                    }
                });
    }
    @Test
    public void testHelloWorld2() throws Exception {
        mockMvc.perform(get("/hi/second")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andDo(new ResultHandler() {
                    @Override
                    public void handle(MvcResult result) throws Exception {
                        String content = result.getResponse().getContentAsString();
                        logger.info("testHelloWorld : {}" ,content);
                    }
                });
    }
    @Test
    public void testJob() throws Exception {
        mockMvc.perform(get("/dynamicJob"))
                .andExpect(status().isOk())
                .andDo(new ResultHandler() {
                    @Override
                    public void handle(MvcResult result) throws Exception {
                        String content = result.getResponse().getContentAsString();
                        logger.info("testJob : {}" ,content);
                    }
                });
    }
}