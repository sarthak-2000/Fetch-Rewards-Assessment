package com.fetchRewards.apiApplication.repository;

import com.fetchRewards.apiApplication.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, String> {
}
