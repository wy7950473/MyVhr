package com.example.hr.model;

public class RespBean {

    private Integer status;

    private String msg;

    private Object object;

    private RespBean(){

    }

    private  RespBean(Integer status,String msg,Object obj){
        this.status = status;
        this.msg = msg;
        this.object = obj;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public static RespBean ok(String msg){
        return new RespBean(200,msg,null);
    }

    public static RespBean ok(String msg,Object object){
        return new RespBean(200,msg,object);
    }

    public static RespBean error(String msg){
        return new RespBean(500,msg,null);
    }

    public static RespBean error(String msg,Object object){
        return new RespBean(500,msg,object);
    }

}
