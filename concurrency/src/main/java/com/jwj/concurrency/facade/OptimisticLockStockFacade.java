package com.jwj.concurrency.facade;

import com.jwj.concurrency.service.OptimisticLockStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

	private final OptimisticLockStockService optimisticLockStockService;

	// 버전 값이 불일치할 경우를 대비한 로직을 작성
	public void decrease(Long productId, Long quantity) throws InterruptedException {
		while (true) {
			try {
				optimisticLockStockService.decrease(productId, quantity);
				break;
			} catch (Exception e) {
				// 버전 값일 일치하지 않는 경우 50ms 뒤에 다시 재요청
				Thread.sleep(50);
			}
		}
	}
}
