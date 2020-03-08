package com.example.polls.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "coffee")
public class Coffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String title;
    
    @NotBlank
    @Size(max = 500)
    private String image;
    
    @NotBlank
    @Size(max = 300)
    private String description;
    
    @NotBlank
    private BigDecimal startPrice;

    @NotBlank
    private String productType;

    private int status;


    

    
    
}
