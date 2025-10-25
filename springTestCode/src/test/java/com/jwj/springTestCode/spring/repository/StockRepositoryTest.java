package com.jwj.springTestCode.spring.repository;

import com.jwj.springTestCode.spring.entity.Stock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StockRepositoryTest {

	@Autowired
	private StockRepository stockRepository;

	@DisplayName("상품번호 리스트로 재고를 조회한다.")
	@Test
	void findAllByProductNumberIn() {
		// given
		Stock stock1 = Stock.createStock("001", 1);
		Stock stock2 = Stock.createStock("002", 2);
		Stock stock3 = Stock.createStock("003", 3);
		stockRepository.saveAll(List.of(stock1, stock2, stock3));

		// when
		List<Stock> stocks = stockRepository.findAllByProductNumberIn(List.of("001", "002"));

		// then
		assertThat(stocks).hasSize(2)
				.extracting("productNumber", "quantity")
				.containsExactlyInAnyOrder(
						tuple("001", 1),
						tuple("002", 2)
				);
	}
}