package com.example.polls.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import  com.example.polls.model.rto.SelectionRto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
	
	private Long id;
    private List<SelectionRto> selection = new ArrayList<SelectionRto>();
    private int productAmount;
    private BigDecimal totalPrice;
    private String orderUid;

}
