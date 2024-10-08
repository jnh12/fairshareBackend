package com.example.fairsharebackend.GPT;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "transactions")
public class GPTResponse {
    @Id
    private String id; // Use String for MongoDB ObjectId or auto-generated string

    private Store store;
    private TransactionDetails transaction;
    private List<Item> items;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Store {
    private String name;
    private String address;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class TransactionDetails {
    private String date; // ISO 8601 format
    private double total; // Use double for number
    private String currency; // ISO 4217 currency code
    private String payment_method; // e.g., "Credit Card", "Cash"
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Item {
    private String name;
    private int quantity; // Use int for number
    private double price; // Use double for number
}

