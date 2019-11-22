package com.example.polls.model;

import com.example.polls.model.rto.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderHistory {
	
   List<OrderRequest> orders = new ArrayList<OrderRequest>();
}