package org.example.leetcode1116;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.IntConsumer;

public class ZeroEvenOddTest {

  @Test
  public void fooBar_when_executed_successfully_PrintFooBarAlternately()
      throws InterruptedException {
    for (int n = 1; n <= 100; n++) {
      ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(n);
      StringBuilder actual = new StringBuilder();
      IntConsumer consumer = actual::append;

      Runnable zero =
          () -> {
            try {
              zeroEvenOdd.zero(consumer);
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          };
      Runnable odd =
          () -> {
            try {
              zeroEvenOdd.odd(consumer);
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          };
      Runnable even =
          () -> {
            try {
              zeroEvenOdd.even(consumer);
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          };

      Thread zeroThread = new Thread(zero);
      Thread eveThread = new Thread(even);
      Thread oddThread = new Thread(odd);
      zeroThread.start();
      eveThread.start();
      oddThread.start();
      zeroThread.join();
      eveThread.join();
      oddThread.join();

      StringBuilder expected = new StringBuilder();
      for (int i = 1; i <= n; i++) {
        expected.append("0");
        expected.append(i);
      }

      Assertions.assertEquals(expected.toString(), actual.toString());
    }
  }
}
