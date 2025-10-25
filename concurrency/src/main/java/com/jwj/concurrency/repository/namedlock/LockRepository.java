package com.jwj.concurrency.repository.namedlock;

import com.jwj.concurrency.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LockRepository extends JpaRepository<Stock, Long> {

	// key 라는 이름의 락을 획득
	@Query(value = "select get_lock(:key, 3000)", nativeQuery = true)
	void getLock(String key);

	// key 라는 이름의 락을 해제
	@Query(value = "select release_lock(:key)", nativeQuery = true)
	void releaseLock(String key);
}
