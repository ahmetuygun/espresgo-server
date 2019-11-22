package com.example.polls.model.rto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.polls.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
	
	List<Order> orders = new ArrayList<Order>();
	private String orderUid;
	private boolean sendLaterOption;
	private boolean sendLaterOk;

}