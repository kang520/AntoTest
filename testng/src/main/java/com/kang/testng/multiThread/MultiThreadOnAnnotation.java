package com.kang.testng.multiThread;

import org.testng.annotations.Test;

//多线程注解实现方法
public class MultiThreadOnAnnotation {
    @Test(invocationCount = 10,threadPoolSize = 3)
    public void test(){
        System.out.println("1");
        System.out.printf("Thread Id : %s%n",Thread.currentThread().getId());
    }
}
