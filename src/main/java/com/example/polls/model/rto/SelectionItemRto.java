package com.example.polls.model.rto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectionItemRto {

    private Long id;
	private String itemName;
	private BigDecimal plusPrice;
}
