package com.kaka.concurrency.example;

import com.kaka.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@NotThreadSafe
public class CountExample1 {
    //请求总数
    public static int clientTotal = 5000;
    //同时并发执行的线程数
    public static int threadTotal = 200;

    public static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i=0;i<clientTotal;i++){
            try {
                semaphore.acquire();
                add();
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}",count);
    }

    public static void add(){
        count++;
    }


}
