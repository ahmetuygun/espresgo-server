package com.example.polls.model.rto;

import com.example.polls.model.Order;
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
public class ApplyCampaingRequest {
	
	List<Order> orders = new ArrayList<Order>();
	private String campaignCode;


}