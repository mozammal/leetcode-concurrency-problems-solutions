package org.example.leetcode1188;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

final class BoundedBlockingQueue {
  private final ReentrantLock lock;
  private int count;
  private final Condition notFull;
  private final Condition notEmpty;
  private int head;
  private int tail;
  private final int[] items;

  BoundedBlockingQueue(int capacity) {
    this.items = new int[capacity];
    lock = new ReentrantLock(true);
    notEmpty = lock.newCondition();
    notFull = lock.newCondition();
  }

  public void enqueue(int element) throws InterruptedException {
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
      while (count == items.length) {
        notFull.await();
      }

      final int[] items = this.items;
      items[tail] = element;
      if (++tail == items.length) {
        tail = 0;
      }
      count++;
      notEmpty.signalAll();
    } finally {
      lock.unlock();
    }
  }

  public int dequeue() throws InterruptedException {
    final ReentrantLock lock = this.lock;
    lock.lock();

    try {
      while (count == 0) {
        notEmpty.await();
      }

      final int[] items = this.items;
      int value = items[head];
      if (++head == items.length) {
        head = 0;
      }
      count--;
      notFull.signalAll();
      return value;
    } finally {
      lock.unlock();
    }
  }

  public int size() {
    ReentrantLock lock = this.lock;
    lock.lock();

    try {
      return count;
    } finally {
      lock.unlock();
    }
  }
}
