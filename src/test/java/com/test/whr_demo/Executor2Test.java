package com.test.whr_demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Oroject dcms
 * @Program com.test.whr_demo.Executor2Test
 * @Description:
 * @Author: haoge
 * @Create: 2019/8/21 20:17
 **/
public class Executor2Test {

  public static AtomicInteger num = new AtomicInteger(10);
  public static int num2 = 0;

  public static void main(String[] args) {
    test6();
  }

  public static void test6() {
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    for (int i = 0; i < 10 ; i++) {
      final int andAdd = ++num2;
      executorService.execute(new Runnable() {
        @Override
        public void run() {
          System.out.println("running--" + andAdd);
        }
      });
    }
    executorService.shutdown();
  }

  public static void test5() {
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    for (int i = 0; i < 10 ; i++) {
      int andAdd;
      synchronized(Executor2Test.class) {
        andAdd = num.getAndAdd(-1);
        System.out.println("start--" + andAdd);
      }
      executorService.execute(new Runnable() {
        @Override
        public void run() {
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
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
//      executorService.submit(new TestTask(num2));
      executorService.execute(new TestTask(num2));
    }
    executorService.shutdown();
  }

}

class TestTask implements Runnable {
  int i;
  TestTask(int i){
    this.i = i;
  }
  @Override
  public void run() {
    System.out.println(i);
  }
}
