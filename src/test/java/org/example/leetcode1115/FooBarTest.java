package org.example.leetcode1115;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FooBarTest {

  @Test
  public void fooBar_when_executed_successfully_PrintFooBarAlternately()
      throws InterruptedException {
    for (int n = 1; n <= 1000; n++) {
      FooBar fooBar = new FooBar(n);
      StringBuilder actual = new StringBuilder();

      Runnable foo =
          () -> {
            try {
              fooBar.foo(() -> actual.append("foo"));
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          };
      Runnable bar =
          () -> {
            try {
              fooBar.bar(() -> actual.append("bar"));
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          };

      Thread fooThread = new Thread(foo);
      Thread barThread = new Thread(bar);
      fooThread.start();
      barThread.start();
      fooThread.join();
      barThread.join();

      StringBuilder expected = new StringBuilder();
      for (int i = 1; i <= n; i++) {
        expected.append("foo");
        expected.append("bar");
      }

      Assertions.assertEquals(expected.toString(), actual.toString());
    }
  }
}
