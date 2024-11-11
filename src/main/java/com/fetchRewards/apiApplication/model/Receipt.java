package com.fetchRewards.apiApplication.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Receipt {

    @Id
    private String id;
    private String retailer;
    private String purchaseDate;
    private String purchaseTime;
    private String total;
    private int points; // Field to store calculated points

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "receipt_id")
    private List<Item> items;

    @Data
    @NoArgsConstructor
    @Entity
    public static class Item {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String shortDescription;
        private String price;
    }
}
