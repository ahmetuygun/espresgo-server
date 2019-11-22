package com.example.polls.model.rto;

import com.example.polls.model.DistrictRto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdressRto {

    private Long id;
     
    private CityRto city;
   
    private DistrictRto district;
    
    private BuildingRto building;
 
    private CompanyRto company;


}
