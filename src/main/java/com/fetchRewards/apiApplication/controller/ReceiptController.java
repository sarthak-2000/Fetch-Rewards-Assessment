package com.fetchRewards.apiApplication.controller;

import com.fetchRewards.apiApplication.model.Receipt;
import com.fetchRewards.apiApplication.repository.ReceiptRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

import static com.fetchRewards.apiApplication.Utils.Utils.calculatePoints;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptRepository receiptRepository;

    public ReceiptController(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @PostMapping("/process")
    public Map<String, String> processReceipt(@RequestBody Receipt receipt) {
        receipt.setId(UUID.randomUUID().toString());
        int points = calculatePoints(receipt);
        receipt.setPoints(points);
        receiptRepository.save(receipt);

        return Map.of("id", receipt.getId());
    }

    @GetMapping("/{id}/points")
    public Map<String, Integer> getPoints(@PathVariable String id) {
        return receiptRepository.findById(id)
                .map(receipt -> Map.of("points", receipt.getPoints()))
                .orElseThrow(() -> new RuntimeException("Receipt not found"));
    }


}
