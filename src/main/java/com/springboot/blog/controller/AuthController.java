package com.springboot.blog.controller;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignupDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> authenticatedUser(@RequestBody LoginDto loginDto){
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
               loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser( @RequestBody SignupDto signupDto){
        //add check for username exists in DB

        if(userRepository.existsByUsername(signupDto.getUsername())){
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }

        //add check for email

        if(userRepository.existsByEmail(signupDto.getEmail())){
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        //  create user object
        User user = new User();
        user.setName(signupDto.getName());
        user.setEmail(signupDto.getEmail());
        user.setUsername(signupDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        if(signupDto.getRole() == null || signupDto.getRole().equals("")){
            Role roles = roleRepository.findByName("ROLE_USER").get();
            user.setRoles(Collections.singleton(roles));
        }else if(signupDto.getRole().equals("admin")) {
            Role roles = roleRepository.findByName("ROLE_ADMIN").get();
            user.setRoles(Collections.singleton(roles));
        }else{
            return new ResponseEntity<>("Not a role", HttpStatus.BAD_REQUEST);
        }

        userRepository.save(user);

        return new ResponseEntity<>("User successfully registered", HttpStatus.OK);
    }
}
