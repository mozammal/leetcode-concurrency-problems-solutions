package org.example.leetcode1226;

final class DiningPhilosophers {
  private final Object[] forks;

  DiningPhilosophers() {
    this.forks = new Object[5];

    for (int i = 0; i < 5; i++) {
      forks[i] = new Object();
    }
  }

  public void wantsToEat(
      int philosopher,
      Runnable pickLeftFork,
      Runnable pickRightFork,
      Runnable eat,
      Runnable putLeftFork,
      Runnable putRightFork) {

    synchronized (this) {
      int rightFork = (philosopher + 1) % 5;

      if (philosopher % 2 == 1) {
        synchronized (forks[philosopher]) {
          pickLeftFork.run();
          synchronized (forks[rightFork]) {
            pickRightFork.run();
            eat.run();
            putRightFork.run();
          }
          putLeftFork.run();
        }
      } else {
        synchronized (forks[rightFork]) {
          pickRightFork.run();
          synchronized (forks[philosopher]) {
            pickLeftFork.run();
            eat.run();
            putLeftFork.run();
          }
          putRightFork.run();
        }
      }
    }
  }
}
