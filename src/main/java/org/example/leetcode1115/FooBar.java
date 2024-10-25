package org.example.leetcode1115;

import java.util.concurrent.Semaphore;

final class FooBar {
  private final int n;
  private final Semaphore foo, bar;

  public FooBar(int n) {
    this.n = n;
    this.foo = new Semaphore(1);
    this.bar = new Semaphore(0);
  }

  public void foo(Runnable printFoo) throws InterruptedException {
    for (int i = 0; i < n; i++) {
      foo.acquire();
      printFoo.run();
      bar.release();
    }
  }

  public void bar(Runnable printBar) throws InterruptedException {
    for (int i = 0; i < n; i++) {
      bar.acquire();
      printBar.run();
      foo.release();
    }
  }
}
