package com.example.polls.model.rto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoffeeDetailRto {
	
	
	private Long id;
    private String image;
    private String description;
    private String title;
    private BigDecimal startPrice;
    private List<SelectionRto> selection = new ArrayList<SelectionRto>();

	
	

}
