package org.example.leetcode1226;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class DiningPhilosophersTest {
  @Test
  public void diningPhilosophers_when_executed_successfully_noDeadlock()
      throws InterruptedException {
    StringBuilder dummyPickFork = new StringBuilder();
    DiningPhilosophers diningPhilosophers = new DiningPhilosophers();
    AtomicIntegerArray integerArray = new AtomicIntegerArray(5);
    Runnable pickLeftFork = () -> dummyPickFork.append("dummyPickFork");
    Thread[] philosophers = new Thread[5];

    for (int i = 0; i < 5; i++) {
      philosophers[i] =
          new Thread(
              new PhilosopherRunnable(
                  new EatRunnable(i, integerArray),
                  pickLeftFork,
                  pickLeftFork,
                  pickLeftFork,
                  pickLeftFork,
                  diningPhilosophers));
    }

    for (Thread philosopher : philosophers) {
      philosopher.start();
    }

    for (Thread philosopher : philosophers) {
      philosopher.join();
    }

    for (int i = 0; i < 5; i++) {
      Assertions.assertEquals(10000, integerArray.get(i));
    }
  }

  private record EatRunnable(int philosopher, AtomicIntegerArray integerArray) implements Runnable {

    @Override
    public void run() {
      integerArray.incrementAndGet(philosopher);
    }
  }

  private record PhilosopherRunnable(
      EatRunnable eatRunnable,
      Runnable pickLetFork,
      Runnable pickRightFork,
      Runnable putLeftFork,
      Runnable putRightFork,
      DiningPhilosophers diningPhilosophers)
      implements Runnable {

    @Override
    public void run() {
      for (int i = 0; i < 10000; i++) {
        diningPhilosophers.wantsToEat(
            eatRunnable.philosopher(),
            pickLetFork,
            pickRightFork,
            eatRunnable,
            putLeftFork,
            putRightFork);
      }
    }
  }
}
