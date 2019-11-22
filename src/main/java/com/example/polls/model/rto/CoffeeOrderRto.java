package com.example.polls.model.rto;

import com.example.polls.model.Adress;
import com.example.polls.model.CoffeeDetail;
import com.example.polls.model.OrderSelection;
import com.example.polls.model.User;
import com.example.polls.model.audit.DateAudit;
import com.example.polls.security.UserPrincipal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoffeeOrderRto {

	private static final long serialVersionUID = 1L;
	private String title;
	private List<OrderSelectionRto> selection;
	private BigDecimal totalPrice;
	private int amount;
	private Date orderDate;
	@JsonIgnore
	private AdressRto address;
	@JsonIgnore
	private UserPrincipal user;
	private Integer status ;



}
