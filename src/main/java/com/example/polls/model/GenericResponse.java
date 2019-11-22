package com.example.polls.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {
	
	private int status;
	private int responseCode;
	private String message;
	private String orderTime;
	private String orderId;
	private String contactNumber;

}
