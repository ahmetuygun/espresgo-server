package com.example.polls.controller;

import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Adress;
import com.example.polls.model.rto.AdressRto;
import com.example.polls.model.rto.BuildingRto;
import com.example.polls.model.rto.CityRto;
import com.example.polls.model.rto.CompanyRto;
import com.example.polls.model.DistrictRto;
import com.example.polls.model.User;
import com.example.polls.payload.*;
import com.example.polls.repository.AddressRepository;
import com.example.polls.repository.PollRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.repository.VoteRepository;
import com.example.polls.security.UserPrincipal;
import com.example.polls.security.CurrentUser;

import com.example.polls.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;


    @Autowired
    AddressRepository addressRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/hasAddress")
    @PreAuthorize("hasRole('USER')")
    public boolean getCurrentUserAddress(@CurrentUser UserPrincipal currentUser) {
        try {
            return addressRepository.findByUser(currentUser.getId()).isPresent();

        } catch (Exception e) {
            return false;
        }

    }


    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {

        AdressRto adressRto = null;

        try {
            Adress adress = addressRepository.findByUser(currentUser.getId()).get();

            CityRto cityRto = CityRto.builder()
                    .name(adress.getCity().getName())
                    .id(adress.getCity().getId())
                    .build();

            DistrictRto districtRto = DistrictRto.builder()
                    .name(adress.getDistrict().getName())
                    .id(adress.getDistrict().getId())
                    .build();

            BuildingRto buildingRto = BuildingRto.builder()
                    .name(adress.getBuilding().getName())
                    .id(adress.getBuilding().getId())
                    .build();

            CompanyRto companyRto = CompanyRto.builder()
                    .name(adress.getCompany().getName())
                    .id(adress.getCompany().getId())
                    .build();

            adressRto = AdressRto.builder()
                    .city(cityRto)
                    .addressDesciption(adress.getAddressDesciption())
                    .district(districtRto)
                    .building(buildingRto)
                    .company(companyRto)
                    .build();
        } catch (Exception e) {
            // TODO: handle exception
        }


        return UserSummary.builder()
                .address(adressRto)
                .userPrincipal(currentUser)
                .build();
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByPhoneAndStatus(username, AppConstants.UserStatus.ACTIVE);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmailAndStatus(email, AppConstants.UserStatus.ACTIVE);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        UserProfile userProfile = new UserProfile(user.getId(), user.getPhone(), user.getName(), user.getCreatedAt());

        return userProfile;
    }


}
