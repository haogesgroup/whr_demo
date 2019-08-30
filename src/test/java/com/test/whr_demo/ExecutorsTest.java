package com.test.whr_demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Oroject dcms
 * @Program com.test.whr_demo.ExecutorsTest
 * @Description:
 * @Author: haoge
 * @Create: 2019/8/21 17:46
 **/
public class ExecutorsTest {

  public static AtomicInteger num = new AtomicInteger(10);
  public static CountDownLatch count = new CountDownLatch(10);
  public static int num2 = -11;

  public static void main(String[] args) {
    test5();
  }

  public static void test5() {
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    for (int i = 0; i < 10 ; i++) {
      int andAdd = num.getAndAdd(-1);
      executorService.execute(new Runnable() {
        @Override
        public void run() {
            System.out.println(andAdd);
         }
      });
    }
    executorService.shutdown();
  }
  public static void test4() {
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    for (int i = 0; i < 10 ; i++) {
      ++num2;
      executorService.execute(new Runnable() {
        @Override
        public void run() {
          System.out.println(num2);
        }
      });
    }
    executorService.shutdown();
  }

  public static void test3() {
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    for (int i = 0; i < 10 ; i++) {
      ++num2;
      executorService.execute(new myTask3(num2));
    }
    executorService.shutdown();

  }

  public static void test2() {
    ExecutorService executorService = Executors.newFixedThreadPool(11);
    executorService.execute(new EndTask(count));
    for (int i = 0; i < 10; i++){
      //executorService.execute(new myTask2(count));
      executorService.execute(new Runnable() {
        @Override
        public void run() {
          try {
            Thread.sleep(1);
            // System.out.println("running...");
          } catch (InterruptedException e) {
            e.printStackTrace();
          } finally {
            synchronized (myTask2.class) {
              count.countDown();
              System.out.println(Thread.currentThread().getName() + "----" + count.getCount());
            }
          }
        }
      });
    }
    /*try {
      count.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }*/

    executorService.shutdown();
    System.out.println("执行完毕");
  }

  private static void test1() {
    ExecutorService executorService = Executors.newFixedThreadPool(5);

    //System.out.println("going---" + Thread.currentThread().getName());
    executorService.shutdown();
    while(!executorService.isTerminated()){}
    System.out.println(num);
  }

}

class EndTask implements Runnable{
  private CountDownLatch endTaskLatch;
  EndTask(CountDownLatch latch) {
    this.endTaskLatch =latch;
  }
  public void run() {
    try {
      endTaskLatch.await();
      System.out.println("开始执行最终任务");
      System.out.println(Thread.currentThread().getName() + "----" + endTaskLatch.getCount());
    }catch(Exception e) {
      e.printStackTrace();
    }
  }
}
class myTask4 implements Runnable {

    AtomicInteger i;
    myTask4(AtomicInteger i){
    this.i = i;
    }
@Override
public void run() {
    try {
    //Thread.sleep(1000);
    System.out.println(i);
    } catch (Exception e) {
    e.printStackTrace();
    }
    }
    }

class myTask3 implements Runnable {

  int i;
  myTask3(int i){
    this.i = i;
  }
  @Override
  public void run() {
    try {
      //Thread.sleep(1000);
      System.out.println(i);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

class myTask2 implements Runnable {

  CountDownLatch i;
  myTask2(CountDownLatch i){
    this.i = i;
  }
  @Override
  public void run() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      synchronized (myTask2.class) {
        i.countDown();
        System.out.println(Thread.currentThread().getName() + "----" + i.getCount());
      }
    }
  }
}

class myTask implements Runnable {

  AtomicInteger i;

  myTask(AtomicInteger i){
    this.i = i;
  }

  @Override
  public void run() {

    System.out.println(Thread.currentThread().getName() + "----" + i.getAndAdd(-1));
  }
}