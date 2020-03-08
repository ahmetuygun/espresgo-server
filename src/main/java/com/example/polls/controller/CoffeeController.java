package com.example.polls.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.polls.model.ApplyCampaignResponse;
import com.example.polls.model.UpdateOrderStatusRequest;
import com.example.polls.model.rto.*;
import com.example.polls.payload.GetOrderByUidRequest;
import com.example.polls.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.polls.model.CoffeeDetail;
import com.example.polls.repository.CoffeeDetailRepository;
import com.example.polls.repository.CoffeeRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.CoffeeService;

/**
 * Created by ahmetuygun
 */
@RestController
@RequestMapping("/api/coffee")
@CacheConfig(cacheNames={"coffee"})
public class CoffeeController {

	@Autowired
	CoffeeRepository coffeeSummaryRepository;

	@Autowired
	CoffeeService coffeeService;

	@Autowired
	CoffeeDetailRepository coffeeDetailRepository;

	@PostMapping("/coffeeSummaryList")
	@Cacheable
	public ResponseEntity<?> getCoffeeSummaryList() {

		List<CoffeeSummaryRto> list = coffeeSummaryRepository.findAll().stream().map(coffeeSum -> {
			CoffeeSummaryRto coffeeSummaryRto = new CoffeeSummaryRto();
			coffeeSummaryRto.setDescription(coffeeSum.getDescription());
			coffeeSummaryRto.setImage(coffeeSum.getImage());
			coffeeSummaryRto.setPrice(coffeeSum.getStartPrice());
			coffeeSummaryRto.setTitle(coffeeSum.getTitle());
			coffeeSummaryRto.setId(coffeeSum.getId());
            coffeeSummaryRto.setProductType(coffeeSum.getProductType());
			return coffeeSummaryRto;
		}).collect(Collectors.toList());

		CoffeeSummarListRto coffeeSummarListRto = new CoffeeSummarListRto();
		coffeeSummarListRto.setList(list);
		return ResponseEntity.ok(coffeeSummarListRto);
	}

	@PostMapping("/getCoffeeDetail/{id}")
	@Cacheable
	public ResponseEntity<?> getCoffeeDetail(@PathVariable(value = "id") Long id) {

		Optional<CoffeeDetail> coffeeDetail = coffeeDetailRepository.findById(id);

		if (coffeeDetail.isPresent()) {

			return ResponseEntity.ok(mapToRto(coffeeDetail.get()));

		}
		return null;

	}

	@PostMapping("/order")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> order(@CurrentUser UserPrincipal currentUser, @RequestBody OrderRequest orderRequest) {

		return ResponseEntity.ok(coffeeService.insertOrder(orderRequest, currentUser));

	}

	@PostMapping("/applyCampaign")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> applyCampaign(@CurrentUser UserPrincipal currentUser, @RequestBody ApplyCampaingRequest orderRequest) {

		if(!AppConstants.ILKKAHVE.equals(orderRequest.getCampaignCode())){
			return ResponseEntity.ok(ApplyCampaignResponse.builder().label("Geçersiz kod :(").discountAmount(BigDecimal.ZERO).build());
		}

		return ResponseEntity.ok(coffeeService.applyCampaign(orderRequest, currentUser));

	}

	@GetMapping("/getOrders")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getOrders(@CurrentUser UserPrincipal currentUser) {

		return ResponseEntity.ok(coffeeService.getOrders(currentUser, AppConstants.HISTORY_METHOD, null));

	}


	@GetMapping("/getNewOrders")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getNewOrders(@CurrentUser UserPrincipal currentUser) {
		return ResponseEntity.ok(coffeeService.getOrders(currentUser, AppConstants.NEWORDERS_METHOD, null));

	}

	@PostMapping("/getOrderByUid")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getOrderByUid(@CurrentUser UserPrincipal currentUser, @RequestBody GetOrderByUidRequest request ) {

		return ResponseEntity.ok(coffeeService.getOrders(currentUser, AppConstants.BY_UID, request.getUid()));

	}


	@PostMapping("/updateOrderStatus")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateOrderStatus(@CurrentUser UserPrincipal currentUser, @RequestBody UpdateOrderStatusRequest request ) {

		return ResponseEntity.ok(coffeeService.updateOrderStatus(request));

	}


	@SuppressWarnings("unchecked")
	private CoffeeDetailRto mapToRto(CoffeeDetail coffeeDetail) {
		// TODO Auto-generated method stub

		return CoffeeDetailRto.builder().id(coffeeDetail.getId()).image(coffeeDetail.getImage())
				.startPrice(coffeeDetail.getCoffee().getStartPrice()).title(coffeeDetail.getCoffee().getTitle())
				.description(coffeeDetail.getCoffee().getDescription())
				.selection(coffeeDetail.getSelection().stream().map(selection -> {
					return SelectionRto.builder().type(selection.getType()).id(selection.getId())
							.label(selection.getLabel()).items(selection.getItems().stream().map(item -> {
								return SelectionItemRto.builder().itemName(item.getItemName())
										.plusPrice(item.getPlusPrice()).id(item.getId()).build();
							}).collect(Collectors.toList())).build();
				}).collect(Collectors.toList())).build();
	}
}
