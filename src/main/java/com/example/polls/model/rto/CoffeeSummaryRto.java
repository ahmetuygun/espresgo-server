package com.example.polls.model.rto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoffeeSummaryRto {

	private String title;
	private BigDecimal price;
	private String description;
	private String image;
	private Long id;
	private String productType;


}
