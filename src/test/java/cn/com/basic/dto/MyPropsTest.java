package cn.com.basic.dto;

import cn.com.basic.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 *
 * read yml config
 * Created by ZhaiJiaYi on 2017/2/14.
 */
public class MyPropsTest extends BaseTest {

    @Autowired
    private MyProps myProps;

    @Test
    public void propsTest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("simpleProp: " + myProps.getSimpleProp());
        System.out.println("arrayProps: " + mapper.writeValueAsString(myProps.getArrayProps()));
        System.out.println("listProp1: " + mapper.writeValueAsString(myProps.getListProp1()));
        System.out.println("listProp2: " + mapper.writeValueAsString(myProps.getListProp2()));
        System.out.println("mapProps: " + mapper.writeValueAsString(myProps.getMapProps()));
    }
}