package com.example.polls.model.rto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import com.example.polls.model.rto.SelectionItemRto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectionRto {
	
	private Long id;
	private String label;
	private String type;
	private List<SelectionItemRto> items = new ArrayList<SelectionItemRto>();




}
