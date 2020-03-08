package com.example.polls.controller;

import com.example.polls.exception.AppException;
import com.example.polls.model.Role;
import com.example.polls.model.RoleName;
import com.example.polls.model.User;
import com.example.polls.payload.*;
import com.example.polls.repository.RoleRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.security.JwtTokenProvider;
import com.example.polls.service.SmsService;
import com.example.polls.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;

/**
 * Created by ahmetuygun
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    SmsService smsService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser (@RequestBody SignUpRequest signUpRequest) {

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(signUpRequest.getEmail()).toUri();

        if(userRepository.existsByPhoneAndStatus(signUpRequest.getPhone(),AppConstants.UserStatus.ACTIVE)) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmailAndStatus(signUpRequest.getEmail(),AppConstants.UserStatus.ACTIVE)) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        Optional<User> oldUser = userRepository.findByEmailAndPhoneAndStatus(signUpRequest.getEmail(),
                signUpRequest.getPhone(),AppConstants.UserStatus.PASSIVE);
        if(oldUser!=null && oldUser.isPresent()){
            User user = oldUser.get();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User result = userRepository.save(user);

        }else{

            User user = new User(signUpRequest.getName(),
                    signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getPhone() );
            user.setKeyManuel(user.getPassword());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new AppException("User Role not set."));

            user.setRoles(Collections.singleton(userRole));
            user.setStatus(AppConstants.UserStatus.PASSIVE);
            User result = userRepository.save(user);


        }

        try {

            if(smsService.sendVerificationCode(signUpRequest.getPhone()))
              return ResponseEntity.created(location).body(new ApiResponse(true, "Telefonunuza bir doğrulama kodu gönderdik."));

        } catch (Exception e) {
            return ResponseEntity.created(location).body(new ApiResponse(false, "Telenonunuza sms gönderemedik. "+  e.getMessage()));

        }
        return ResponseEntity.created(location).body(new ApiResponse(false, "Telenonunuza sms gönderemedik. "));

    }


    @GetMapping("/activateUser")
    public ResponseEntity<?>  activateUser(@RequestParam(value = "otp") String otp, @RequestParam(value = "to") String to) {


        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(to).toUri();
        try {
            if (smsService.checkVerification(to, otp)) {
                User user = userRepository.findByPhone(to).get();

                user.setStatus(AppConstants.UserStatus.ACTIVE);
                userRepository.save(user);

                if (user == null)
                    return new  ResponseEntity<>(
                            "Kullanıcı bulunamadı",
                            HttpStatus.BAD_REQUEST);


                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getEmail(),
                                user.getKeyManuel()
                        )
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = tokenProvider.generateToken(authentication);


                return ResponseEntity.created(location).body(new ApiResponse(true,
                        jwt));
            }
            return ResponseEntity.created(location).body(new ApiResponse(false,
                    "Kod doğrulanamadı"));
        } catch (Exception e) {
            return ResponseEntity.created(location).body(new ApiResponse(false,
                    "Birşeyler yanlış gitti"));
        }
    }

    @GetMapping("/sendCode")
    public ResponseEntity<?> sendCode(@RequestParam(value = "to") String to) {


        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(to).toUri();
        try {
            if(smsService.sendVerificationCode(to))
             return ResponseEntity.created(location).body(new ApiResponse(true, "Telefonunuza doğrulama kodu gönderdik."));
        } catch (Exception e) {
            return ResponseEntity.created(location).body(new ApiResponse(false, "Telefonunuza kod gönderemedik"));
        }

        return ResponseEntity.created(location).body(new ApiResponse(false,
                "Birşeyler yanlış gitti"));


    }

    }
