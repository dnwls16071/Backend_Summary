package com.jwj.springTestCode.spring.repository;

import com.jwj.springTestCode.spring.history.MailSendHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailSendRepository extends JpaRepository<MailSendHistory, Long> {
}
