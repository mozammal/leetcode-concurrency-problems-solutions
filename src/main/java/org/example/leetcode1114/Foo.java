package org.example.leetcode1114;

import java.util.concurrent.Semaphore;

final class Foo {
  private final Semaphore secondMethodArrived, thirdMethodArrived;

  Foo() {
    this.secondMethodArrived = new Semaphore(0);
    this.thirdMethodArrived = new Semaphore(0);
  }

  void first(Runnable printFirst) throws InterruptedException {
    printFirst.run();
    secondMethodArrived.release();
  }

  void second(Runnable printSecond) throws InterruptedException {
    secondMethodArrived.acquire();
    printSecond.run();
    thirdMethodArrived.release();
  }

  void third(Runnable printThird) throws InterruptedException {
    thirdMethodArrived.acquire();
    printThird.run();
    secondMethodArrived.release();
  }
}
