package com.example.polls.model.rto;

import com.example.polls.model.Order;
import com.example.polls.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCheckOutRto implements Comparable  {
	
	List<CoffeeOrderRto> orders = new ArrayList<CoffeeOrderRto>();
	private String orderUid;
	private BigDecimal checkOutPrice;
	private String orderDate;
	private String status;
	private Date orderAsDate;
	private AdressRto address;
	private UserPrincipal user;
	private Integer statusId;


	@Override
	public int compareTo(Object o) {
		return (((OrderCheckOutRto)o).orderAsDate).compareTo(this.orderAsDate);
	}
}