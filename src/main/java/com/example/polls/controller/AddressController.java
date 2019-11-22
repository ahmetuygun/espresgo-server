package com.example.polls.controller;

import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.AddressRequest;
import com.example.polls.model.Adress;
import com.example.polls.model.Building;
import com.example.polls.model.City;
import com.example.polls.model.Company;
import com.example.polls.model.District;
import com.example.polls.model.GenericKeyValue;
import com.example.polls.model.GenericKeyValueResponse;
import com.example.polls.model.User;
import com.example.polls.payload.*;
import com.example.polls.repository.AddressRepository;
import com.example.polls.repository.BuildingRepository;
import com.example.polls.repository.CityRepository;
import com.example.polls.repository.CompanyRepository;
import com.example.polls.repository.DistrictRepository;
import com.example.polls.repository.PollRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.repository.VoteRepository;
import com.example.polls.security.UserPrincipal;
import com.example.polls.security.CurrentUser;
import com.example.polls.util.AppConstants;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AddressController {

	@Autowired
	CityRepository cityRepository;

	@Autowired
	DistrictRepository districtRepository;

	@Autowired
	BuildingRepository buildingRepository;

	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

	@GetMapping("/address/getCities")
	public GenericKeyValueResponse cities() {

		return GenericKeyValueResponse.builder().list(cityRepository.findAll().stream().map(city -> {
			return GenericKeyValue.builder().id(city.getId()).name(city.getName()).build();
		}).collect(Collectors.toList())).build();
	}

	@GetMapping("/address/getDistrictByCity")
	public GenericKeyValueResponse getDistrictByCity(@RequestParam(value = "cityId") Long cityId) {

		return GenericKeyValueResponse.builder().list(districtRepository.findByCity(cityId).stream().map(district -> {
			return GenericKeyValue.builder().id(district.getId()).name(district.getName()).build();
		}).collect(Collectors.toList())).build();
	}

	@GetMapping("/address/getBuildingByDistrict")
	public GenericKeyValueResponse getBuildingByDistrict(@RequestParam(value = "distirictId") Long distirictId) {

		return GenericKeyValueResponse.builder()
				.list(buildingRepository.findByDistrict(distirictId).stream().map(district -> {
					return GenericKeyValue.builder().id(district.getId()).name(district.getName()).build();
				}).collect(Collectors.toList())).build();
	}

	@GetMapping("/address/getCompanyByBuilding")
	public GenericKeyValueResponse getCompanyByBuilding(@RequestParam(value = "buildingId") Long buildingId) {

		return GenericKeyValueResponse.builder()
				.list(companyRepository.findByBuilding(buildingId).stream().map(district -> {
					return GenericKeyValue.builder().id(district.getId()).name(district.getName()).build();
				}).collect(Collectors.toList())).build();
	}
	
	
    
    @PostMapping("/address")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> address(@Valid @RequestBody AddressRequest addressRequest,@CurrentUser UserPrincipal currentUser) {

    	
    	Optional<Adress> adress = addressRepository.findByUser(currentUser.getId());
    	
    
    		addressRepository.save( Adress.builder().id( adress.isPresent() ? adress.get().getId() : null )
			.city(new City(addressRequest.getCity_id()))
			.district(new District(addressRequest.getDistrict_id()))
			.building(new Building(addressRequest.getBuilding_id()))
			.company(new Company(addressRequest.getCompany_id()))
			.user(new User(currentUser.getId()))
			.addressDesciption(addressRequest.getAddressDesciption())
			.build());
    	
    
    	
    	User user = userRepository.findById(currentUser.getId()).get();
    	user.setName(addressRequest.getName());
    	user.setEmail(addressRequest.getEmail());
    	user.setPhone( addressRequest.getPhone());
    	
    	userRepository.save(user);
        return ResponseEntity.ok("Success");
    }
	
	
}
