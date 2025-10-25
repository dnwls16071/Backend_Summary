package com.jwj.concurrency.service;

import com.jwj.concurrency.domain.Stock;
import com.jwj.concurrency.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {

	private final StockRepository stockRepository;

	// 재고 감소 로직
	/*
	* synchronized 적용 시 문제점
	  * synchronized 키워드는 한 프로세스 내에서만 동시성 제어가 가능하다.
	  * 하지만 실제로 운영되는 서버의 경우 여러 개의 프로세스가 동시에 동작하기 때문에 동시성 제어가 불가능하다.
	 */
	@Transactional
	public synchronized void decrease(Long productId, Long quantity) {
		Stock stock = stockRepository.findById(productId).orElseThrow();
		stock.decrease(quantity);
		stockRepository.saveAndFlush(stock);
	}
}
