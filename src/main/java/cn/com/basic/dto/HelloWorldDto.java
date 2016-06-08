package cn.com.basic.dto;

/**
 * Created by zhaijiayi on 2016/5/3.
 */
public class HelloWorldDto {
    private String type;
    public  HelloWorldDto(String type){
        this.type = type;
    }
    private String name;
    private Integer id;
    private String sex = "sss";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        if(type.equals("girl")){
            this.sex = "hahaha";
        }else this.sex = sex;

    }
}
