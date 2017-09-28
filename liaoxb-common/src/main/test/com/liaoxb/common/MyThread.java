package com.liaoxb.common;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/9/27 16:24:24
 */
public class MyThread implements Runnable {
    String  str = "123456";
    String userName="1111";

    public MyThread(String userName) {
        this.userName = userName;
    }

    @Override
    public void run() {
        System.out.println(str);
        System.out.println(userName);
    }

    public static void str(){
        System.out.println("-------------");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("9876548516");
    }
}
