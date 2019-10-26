package com.kang.testng.suite;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

//进行所有测试套件配置，共有的内容
public class SuiteConfig {
    @BeforeSuite
    public void beforeSuite(){
        System.out.println("before suite 运行啦");
    }
    @AfterSuite
    public void afterSuite(){
        System.out.println("after suite 运行啦");
    }
    @BeforeTest
    public void beforeTest(){
        System.out.println("BeforeTest");
    }
    @AfterTest
    public void afterTest(){
        System.out.println("AfterTest");
    }
}
