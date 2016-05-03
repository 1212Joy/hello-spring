package cn.com.basic;

import cn.com.basic.config.ApplicationConfiguration;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by zhaijiayi on 2016/5/3.
 */
/*

*/
/*@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationConfiguration.class)*/


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = ApplicationConfiguration.class)
public class BaseTest {
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void initMock() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
