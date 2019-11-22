package com.example.polls.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {

    private String name;

    private String phone;

    private String email;

    private String password;
    
}
