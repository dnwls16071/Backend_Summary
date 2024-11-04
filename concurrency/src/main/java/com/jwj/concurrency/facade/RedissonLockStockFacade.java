package com.jwj.concurrency.facade;

import com.jwj.concurrency.service.StockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedissonLockStockFacade {

	private RedissonClient redissonClient;
	private StockService stockService;

	public RedissonLockStockFacade(RedissonClient redissonClient, StockService stockService) {
		this.redissonClient = redissonClient;
		this.stockService = stockService;
	}

	public void decrease(Long productId, Long quantity) {
		RLock lock = redissonClient.getLock(productId.toString());

		try {
			boolean b = lock.tryLock(10, 1, TimeUnit.SECONDS);

			if (!b) {
				System.out.println("lock 획득 실패");
				return;
			}
			stockService.decrease(productId, quantity);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}
	}
}
