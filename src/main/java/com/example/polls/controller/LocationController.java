package com.example.polls.controller;

import com.example.polls.model.VehicleLocationRto;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ahmetuygun
 */
@RestController
@RequestMapping("/api/location")
public class LocationController {

	@Autowired
	LocationService locationService;


	@PostMapping("/insertLocation")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> insertLocation( @RequestBody VehicleLocationRto location) {
		return ResponseEntity.ok(locationService.insertVehicleLocation(location));
	}

	@GetMapping("/getLastLocation")
	public ResponseEntity<?> getLastLocation() {
		return ResponseEntity.ok(locationService.getLastLocation());
	}


	@PostMapping("/inserUserLocation")
	public ResponseEntity<?> inserUserLgetLastLocationocation(@CurrentUser UserPrincipal currentUser, @RequestBody  VehicleLocationRto location) {
		return ResponseEntity.ok(locationService.inserUserLocation(location, currentUser));
	}

}
