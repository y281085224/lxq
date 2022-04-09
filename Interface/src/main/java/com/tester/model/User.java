package com.tester.model;


import lombok.Data;

@Data
public class User {
    //这里的属性修饰 可以不和数据库中定义的一样；因为获取数据后用这个文件接收；会被重新定义成这里设置的修饰；String
    private int id;
    private String userName;
    private String password;
    private String age;
    private String sex;
    private String permission;
    private String isDelete;
    private String dddddddddddddddddd;  // 这个类是用来修饰用的；实际的参数可以多于用到的参数；这个参数就是多出来的

}
