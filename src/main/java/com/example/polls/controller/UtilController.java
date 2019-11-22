package com.example.polls.controller;

import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.UtilService;
import com.example.polls.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */
@RestController
@RequestMapping("/api/util")
public class UtilController {

	@Autowired
	UtilService utilService;

	@GetMapping("/closeLocation")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> closeLocation(@CurrentUser UserPrincipal currentUser) {
		return ResponseEntity.ok(utilService.set(currentUser, AppConstants.LOCATION_STATUS,AppConstants.CLOSE ));
	}

	@GetMapping("/openLocation")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> openLocation(@CurrentUser UserPrincipal currentUser) {
		return ResponseEntity.ok(utilService.set(currentUser, AppConstants.LOCATION_STATUS,AppConstants.OPEN ));
	}

	@GetMapping("/closeOrder")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> closeOrder(@CurrentUser UserPrincipal currentUser) {
		return ResponseEntity.ok(utilService.set(currentUser, AppConstants.ORDER_STATUS,AppConstants.CLOSE ));
	}


	@GetMapping("/openOrder")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> openOrder(@CurrentUser UserPrincipal currentUser) {
		return ResponseEntity.ok(utilService.set(currentUser, AppConstants.ORDER_STATUS,AppConstants.OPEN ));
	}


	@GetMapping("/isLocationOpen")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> isOrderOpen() {
		return ResponseEntity.ok(utilService.get(AppConstants.LOCATION_STATUS,AppConstants.OPEN ));
	}

	@GetMapping("/isOrderClosed")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> isLocationOpen() {
		return ResponseEntity.ok(utilService.get(AppConstants.ORDER_STATUS,AppConstants.CLOSE ));
	}

}
