package com.example.polls.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequest {

	private Long city_id;
	private Long district_id;
	private Long building_id;
	private Long company_id;
	private Long user_id;
	private String name;
	private String phone;
	private String email;
	private String addressDesciption;

}
