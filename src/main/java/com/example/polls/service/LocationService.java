package com.example.polls.service;

import com.example.polls.model.*;
import com.example.polls.repository.UserLocationRepository;
import com.example.polls.repository.VehicleLocationRepository;
import com.example.polls.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LocationService {

    @Autowired
    VehicleLocationRepository vehicleLocationRepository;

    @Autowired
    UserLocationRepository userLocationRepository;


    public boolean insertVehicleLocation(VehicleLocationRto location) {

        try {
            vehicleLocationRepository.save(VehicleLocation.builder()
                    .insertDate(new Date())
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .vehicle(new Vehicle(new Long(1), "Scudo"))
                    .build()
            );
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public VehicleLocationRto getLastLocation() {

        VehicleLocation location = vehicleLocationRepository.findLast().get();

        return VehicleLocationRto.builder()
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();

    }

    public boolean inserUserLocation(VehicleLocationRto location, UserPrincipal currentUser) {

        userLocationRepository.save(UserLocation.builder()
                .insertDate(new Date())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .user(new User(currentUser.getId()))
                .build());
        return true;
    }
}
