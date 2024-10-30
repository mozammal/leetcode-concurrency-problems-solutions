package org.example.leetcode1188;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class BoundedBlockingQueueTest {
  private static final long TIMEOUT = 1000;
  private static final int N = 20;
  private static final int ITERATIONS = 1000;

  @Test
  public void boundedBlockingQueue_when_constructed_thenEmpty() {
    final BoundedBlockingQueue boundedBlockingQueue = new BoundedBlockingQueue(5);
    Assertions.assertEquals(boundedBlockingQueue.size(), 0);
  }

  @Test
  public void boundedBlockingQueue_when_enqueueAllItems_thenFull() throws Exception {
    final BoundedBlockingQueue boundedBlockingQueue = new BoundedBlockingQueue(5);
    for (int i = 1; i <= 5; i++) {
      boundedBlockingQueue.enqueue(i);
    }
    Assertions.assertEquals(boundedBlockingQueue.size(), 5);
  }

  // Test blocking operations
  @Test
  public void boundedBlockingQueue_when_empty_thenDequeueBlocked() throws InterruptedException {
    final BoundedBlockingQueue boundedBlockingQueue = new BoundedBlockingQueue(5);
    final Thread consumer =
        new Thread(
            () -> {
              try {
                int element = boundedBlockingQueue.dequeue();
              } catch (InterruptedException e) {

              }
            });

    consumer.start();
    Thread.sleep(TIMEOUT);
    consumer.interrupt();
    consumer.join(TIMEOUT);
    Assertions.assertFalse(consumer.isAlive());
  }

  // Test safety aspect by running multiple concurrent producers and consumers and check the enqued
  // sum with the sum dequed out of the bounded blocking queue
  @Test
  public void boundedBlockingQueue_when_nProducersAndConsumers_thenenqSumEqualsdeqSum()
      throws InterruptedException, BrokenBarrierException {
    final BoundedBlockingQueue boundedBlockingQueue = new BoundedBlockingQueue(5);
    final ExecutorService threadPool = Executors.newCachedThreadPool();
    final CyclicBarrier cyclicBarrier = new CyclicBarrier(N * 2 + 1);
    final AtomicInteger enqSum = new AtomicInteger(0);
    final AtomicInteger deqSum = new AtomicInteger(0);

    for (int i = 1; i <= N; i++) {
      threadPool.execute(new Producer(cyclicBarrier, boundedBlockingQueue, enqSum, new Random()));
      threadPool.execute(new Consumer(cyclicBarrier, boundedBlockingQueue, deqSum));
    }

    cyclicBarrier.await();
    cyclicBarrier.await();
    Assertions.assertEquals(deqSum.get(), enqSum.get());
  }

  private record Producer(
      CyclicBarrier cyclicBarrier,
      BoundedBlockingQueue boundedBlockingQueue,
      AtomicInteger enqSum,
      Random random)
      implements Runnable {

    public void run() {
      try {
        int sum = 0;
        cyclicBarrier.await();
        for (int i = 1; i <= ITERATIONS; i++) {
          int item = random.nextInt(5000);
          boundedBlockingQueue.enqueue(item);
          sum += item;
        }
        enqSum.getAndAdd(sum);
        cyclicBarrier.await();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  private record Consumer(
      CyclicBarrier cyclicBarrier, BoundedBlockingQueue boundedBlockingQueue, AtomicInteger deqSum)
      implements Runnable {

    public void run() {
      try {
        int sum = 0;
        cyclicBarrier.await();
        for (int i = 1; i <= ITERATIONS; i++) {
          int item = boundedBlockingQueue.dequeue();
          sum += item;
        }
        deqSum.getAndAdd(sum);
        cyclicBarrier.await();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
