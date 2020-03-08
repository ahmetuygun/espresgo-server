package com.example.polls.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ApplyCampaignResponse {
	
	private String label;
	private BigDecimal discountAmount;

}
