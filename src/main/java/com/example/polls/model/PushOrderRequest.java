package com.example.polls.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PushOrderRequest {

    private String orderUid;
    private String status;
    private String orderDate;
    private String orderItemNames;
    private String addessText;


}
