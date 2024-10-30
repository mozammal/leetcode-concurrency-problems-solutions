package org.example.leetcode1195;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

final class FizzBuzz {
  private final int n;

  private final Semaphore fizz, buzz, fizzbuzz, number;

  public FizzBuzz(int n) {
    this.n = n;
    fizz = new Semaphore(0);
    buzz = new Semaphore(0);
    fizzbuzz = new Semaphore(0);
    number = new Semaphore(1);
  }

  public void fizz(Runnable printFizz) throws InterruptedException {
    for (int i = 3; i <= n; i += 3) {
      if (i % 3 == 0 && i % 5 != 0) {
        fizz.acquire();
        printFizz.run();
        releaseSemaphoreFoeNextNumber(i + 1);
      }
    }
  }

  public void buzz(Runnable printBuzz) throws InterruptedException {
    for (int i = 5; i <= n; i += 5) {
      if (i % 5 == 0 && i % 3 != 0) {
        buzz.acquire();
        printBuzz.run();
        releaseSemaphoreFoeNextNumber(i + 1);
      }
    }
  }

  public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
    for (int i = 15; i <= n; i += 15) {
      if (i % 15 == 0) {
        fizzbuzz.acquire();
        printFizzBuzz.run();
        releaseSemaphoreFoeNextNumber(i + 1);
      }
    }
  }

  public void number(IntConsumer printNumber) throws InterruptedException {
    for (int i = 1; i <= n; i++) {
      if (i % 3 != 0 && i % 5 != 0) {
        number.acquire();
        printNumber.accept(i);
        releaseSemaphoreFoeNextNumber(i + 1);
      }
    }
  }

  void releaseSemaphoreFoeNextNumber(int nextNumber) {
    if (nextNumber % 3 == 0 && nextNumber % 5 != 0) {
      fizz.release();
    } else if (nextNumber % 5 == 0 && nextNumber % 3 != 0) {
      buzz.release();
    } else if (nextNumber % 15 == 0) {
      fizzbuzz.release();
    } else {
      number.release();
    }
  }
}
