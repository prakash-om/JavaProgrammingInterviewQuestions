package threads;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerWIthLock {

	Condition isFullCondition;
	Condition isEmptyCondition;
	Lock lock;
	int capcity;
	Queue queue;

	ConcurrentHashMap map = new ConcurrentHashMap();

	public ProducerConsumerWIthLock(int capacity) {
		this.capcity = capacity;
		lock = new ReentrantLock();
		isFullCondition = lock.newCondition();
		isEmptyCondition = lock.newCondition();
		queue = new LinkedList();
		Collections.synchronizedMap(map);
	}

	void put(int data) {

		lock.lock();

		try {
			while (queue.size() == capcity) {
				try {
					isFullCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			queue.add(data);
			isEmptyCondition.signalAll();

		} finally {
			lock.unlock();
		}

	}

	void get() {

		lock.lock();

		try {

			while (queue.size() == 0) {
				try {
					isEmptyCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			System.out.println("get() :" + queue.poll());
		} finally {
			lock.unlock();
		}
	}

}
