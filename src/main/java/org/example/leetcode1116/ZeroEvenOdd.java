package org.example.leetcode1116;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

final class ZeroEvenOdd {
  private final int n;

  private final Semaphore zeroMethodCallArrived, evenMethodCallArrived, oddMethodCallArrived;

  ZeroEvenOdd(int n) {
    this.n = n;
    this.zeroMethodCallArrived = new Semaphore(1);
    this.evenMethodCallArrived = new Semaphore(0);
    this.oddMethodCallArrived = new Semaphore(0);
  }

  public void zero(IntConsumer printNumber) throws InterruptedException {
    for (int i = 1; i <= n; i++) {
      zeroMethodCallArrived.acquire();
      printNumber.accept(0);
      releaseSemaphoreBasedOnParity(i);
    }
  }

  private void releaseSemaphoreBasedOnParity(int curNumber) {
    if (curNumber % 2 == 1) {
      oddMethodCallArrived.release();
    } else {
      evenMethodCallArrived.release();
    }
  }

  public void even(IntConsumer printNumber) throws InterruptedException {
    for (int i = 2; i <= n; i += 2) {
      evenMethodCallArrived.acquire();
      printNumber.accept(i);
      zeroMethodCallArrived.release();
    }
  }

  public void odd(IntConsumer printNumber) throws InterruptedException {
    for (int i = 1; i <= n; i += 2) {
      oddMethodCallArrived.acquire();
      printNumber.accept(i);
      zeroMethodCallArrived.release();
    }
  }
}
