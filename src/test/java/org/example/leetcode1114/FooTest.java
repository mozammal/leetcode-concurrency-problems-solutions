package org.example.leetcode1114;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FooTest {
  @Test
  public void foo_when_executed_successfully_PrintFirstSecondThirdSequentially()
      throws InterruptedException {

    for (int n = 1; n <= 100; n++) {

      StringBuilder actual = new StringBuilder();
      Foo foo = new Foo();
      Runnable runnableA =
          () -> {
            try {
              foo.first(() -> actual.append("first"));
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          };
      Runnable runnableB =
          () -> {
            try {
              foo.second(() -> actual.append("second"));
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          };
      Runnable runnableC =
          () -> {
            try {
              foo.third(() -> actual.append("third"));
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          };

      Thread threadA = new Thread(runnableA);
      Thread threadB = new Thread(runnableB);
      Thread threadC = new Thread(runnableC);
      List<Thread> threads = Arrays.asList(threadA, threadB, threadC);
      Collections.shuffle(threads);
      for (Thread thread : threads) {
        thread.start();
      }
      for (Thread thread : threads) {
        thread.join();
      }

      String expected = "firstsecondthird";
      Assertions.assertEquals(expected, actual.toString());
    }
  }
}
