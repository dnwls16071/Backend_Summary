package com.jwj.concurrency.facade.optimistic;

import com.jwj.concurrency.service.optimistic.OptimisticLockStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

	private final OptimisticLockStockService optimisticLockStockService;

	public void decrease(Long productId, Long quantity) throws InterruptedException {
		while (true) {
			try {
				// 재고 감소 로직을 실행
				optimisticLockStockService.decrease(productId, quantity);
				break;
			} catch (Exception e) {
				// Version 필드의 값이 일치하지 않는 경우 50ms 뒤에 다시 재요청을 보낸다.
				Thread.sleep(50);
			}
		}
	}
}
