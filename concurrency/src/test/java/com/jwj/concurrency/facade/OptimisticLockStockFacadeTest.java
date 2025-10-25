package com.jwj.concurrency.facade;

import com.jwj.concurrency.domain.Stock;
import com.jwj.concurrency.facade.optimistic.OptimisticLockStockFacade;
import com.jwj.concurrency.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OptimisticLockStockFacadeTest {

	@Autowired private OptimisticLockStockFacade stockFacade;
	@Autowired private StockRepository stockRepository;

	@BeforeEach
	public void before() {
		stockRepository.saveAndFlush(new Stock(1L, 100L));
	}

	@AfterEach
	public void after() {
		stockRepository.deleteAll();
	}

	@Test
	@DisplayName("동시에 여러 요청을 통한 상품 주문시 요청만큼의 재고 감소가 이루어진다. - 낙관적 락 도입")
	void 동시에_여러_요청을_통한_상품_주문시_요청만큼의_재고_감소가_이루어진다() throws InterruptedException {
		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					stockFacade.decrease(1L, 1L);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		Stock stock = stockRepository.findById(1L).orElseThrow();

		/*
		 * 낙관적 락을 사용하는 경우 -> @Lock(LockModeType.OPTIMISTIC) & @Version 사용
		 */
		assertThat(stock.getQuantity()).isEqualTo(0L);
	}
}