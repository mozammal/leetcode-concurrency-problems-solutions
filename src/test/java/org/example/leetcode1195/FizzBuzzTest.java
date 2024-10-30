package org.example.leetcode1195;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.IntConsumer;

public class FizzBuzzTest {

  @Test
  public void fizzBuzz_when_executed_successfully_PrintSequence() throws InterruptedException {
    for (int n = 1; n <= 50; n++) {
      FizzBuzz fizzBuzz = new FizzBuzz(n);
      StringBuilder actual = new StringBuilder();

      Runnable fizz =
          () -> {
            try {
              fizzBuzz.fizz(() -> actual.append("fizz"));
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          };
      Runnable buzz =
          () -> {
            try {
              fizzBuzz.buzz(() -> actual.append("buzz"));
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          };
      Runnable fizzbuzz =
          () -> {
            try {
              fizzBuzz.fizzbuzz(() -> actual.append("fizzbuzz"));
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          };
      IntConsumer intConsumer = actual::append;
      Runnable number =
          () -> {
            try {
              fizzBuzz.number(intConsumer);
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          };

      Thread A = new Thread(fizz);
      Thread B = new Thread(buzz);
      Thread C = new Thread(fizzbuzz);
      Thread D = new Thread(number);

      A.start();
      B.start();
      C.start();
      D.start();
      A.join();
      B.join();
      C.join();
      D.join();

      StringBuilder expected = new StringBuilder();
      for (int i = 1; i <= n; i++) {
        if (i % 3 == 0 && i % 5 != 0) {
          expected.append("fizz");
        } else if (i % 5 == 0 && i % 3 != 0) {
          expected.append("buzz");
        } else if (i % 15 == 0) {
          expected.append("fizzbuzz");
        } else {
          expected.append(i);
        }
      }

      Assertions.assertEquals(expected.toString(), actual.toString());
    }
  }
}
