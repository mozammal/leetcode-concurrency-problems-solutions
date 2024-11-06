package org.example.leetcode1117;

import java.util.concurrent.Semaphore;

final class H2O {
  private final Semaphore oxygenSemaphore, hydryogenSemaphore;

  H2O() {
    this.oxygenSemaphore = new Semaphore(2);
    this.hydryogenSemaphore = new Semaphore(0);
  }

  public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
    hydryogenSemaphore.acquire();
    releaseHydrogen.run();
    oxygenSemaphore.release();
  }

  public void oxygen(Runnable releaseOxygen) throws InterruptedException {
    oxygenSemaphore.acquire(2);
    releaseOxygen.run();
    hydryogenSemaphore.release(2);
  }
}
