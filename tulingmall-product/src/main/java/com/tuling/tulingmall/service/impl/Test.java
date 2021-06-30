package com.tuling.tulingmall.service.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {


    /**
     * .
     * 方法执行完成后的处理
     **/
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程:"+ Thread.currentThread(). getId());
            int i = 10 /0;
            System.out.println("运行结果: " + i);
            return i;
        }, executor).handle((res, thr) -> {
            System.out.println(1);
            return 0;
        });
        Integer integer = null;
        try {
            integer = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("main.... end...." + integer);


    }
}