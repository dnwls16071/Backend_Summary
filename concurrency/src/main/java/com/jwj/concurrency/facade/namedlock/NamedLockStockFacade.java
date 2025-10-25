package com.jwj.concurrency.facade.namedlock;

import com.jwj.concurrency.repository.namedlock.LockRepository;
import com.jwj.concurrency.service.namedlock.NamedLockStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NamedLockStockFacade {

	private final LockRepository lockRepository;
	private final NamedLockStockService stockService;

	@Transactional
	public void decrease(Long productId, Long quantity) {
		try {
			lockRepository.getLock(productId.toString());
			stockService.decrease(productId, quantity);
		} finally {
			lockRepository.releaseLock(productId.toString());
		}
	}
}
