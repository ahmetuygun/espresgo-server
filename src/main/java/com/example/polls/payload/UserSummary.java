package com.example.polls.payload;

import com.example.polls.security.UserPrincipal;
import com.example.polls.model.rto.AdressRto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSummary {
	
	private UserPrincipal userPrincipal;
	private AdressRto address;
	

}
