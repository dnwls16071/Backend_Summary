package com.jwj.concurrency.facade;

import com.jwj.concurrency.repository.RedisLockRepository;
import com.jwj.concurrency.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {

	private final RedisLockRepository redisLockRepository;
	private final StockService stockService;

	public void decrease(Long productId, Long quantity) throws InterruptedException {
		while (!redisLockRepository.lock(productId)) {
			Thread.sleep(100);
		}

		try {
			stockService.decrease(productId, quantity);
		} finally {
			redisLockRepository.unlock(productId);
		}
	}
}
