package org.example.leetcode1117;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class H2OTest {
  @Test
  public void fooBar_when_executed_successfully_PrintFooBarAlternately()
      throws InterruptedException {
    for (int n = 1; n <= 20; n++) {
      int j = n;
      StringBuilder actual = new StringBuilder();
      H2O h2O = new H2O();

      Runnable H = () -> actual.append("H");
      Runnable O = () -> actual.append("O");

      Thread oxygen =
          new Thread(
              () -> {
                try {
                  for (int i = 0; i < j; i++) {
                    h2O.oxygen(O);
                  }
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }
              });
      Thread hydrogen =
          new Thread(
              () -> {
                try {
                  for (int i = 0; i < 2 * j; i++) {
                    h2O.hydrogen(H);
                  }
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }
              });

      oxygen.start();
      hydrogen.start();
      oxygen.join();
      hydrogen.join();

      Assertions.assertEquals(actual.length(), 3 * n);

      for (int i = 0; i < actual.length() - 2; i += 3) {
        int countOxygen = 0, countHydrogen = 0;
        for (int k = i; k < i + 3; k++) {
          if (actual.charAt(k) == 'H') {
            countHydrogen++;
          } else if (actual.charAt(k) == 'O') {
            countOxygen++;
          }
        }
        Assertions.assertEquals(2, countHydrogen);
        Assertions.assertEquals(1, countOxygen);
      }
    }
  }
}
