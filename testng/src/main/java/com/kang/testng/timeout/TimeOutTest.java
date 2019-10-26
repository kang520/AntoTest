package com.kang.testng.timeout;

import org.testng.annotations.Test;

public class TimeOutTest {
    @Test(timeOut = 3000)//单位为毫秒值
    public void testSuccess() throws InterruptedException {
        System.out.println("测试成功");
        Thread.sleep(2000);
    }
    @Test(timeOut = 2000)//单位为毫秒值
    public void testFailed() throws InterruptedException {
        System.out.println("测试失败");
        Thread.sleep(3000);
    }
}
