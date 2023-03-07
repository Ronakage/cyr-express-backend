package com.ronymawad.cyrexpress.shop.controllers;

import com.ronymawad.cyrexpress.shop.models.UserModel;
import com.ronymawad.cyrexpress.shop.services.UserService;
import com.ronymawad.cyrexpress.shop.request.SignUpUserRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v0/shop/users")
public class UserController {
    private final UserService userService;

    //Login and registration
    @PreAuthorize("hasAuthority('ROLE_STAFF')")
    @PostMapping("/signup")
    public ResponseEntity<Object> signupUser(@RequestBody SignUpUserRequest request) {
        try {
            userService.signUpUser(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("message", e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User created with " + request.email()));
    }

    @GetMapping("/signin")
    public ResponseEntity<Object> signInUser(Authentication authentication){
        try {
             return ResponseEntity.status(HttpStatus.OK).body(userService.signInUser(authentication));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/working")
    public ResponseEntity<Object> changeWorkingStatus(Authentication authentication){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.changeWorkingStatus(authentication));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("message", e.getMessage()));
        }
    }

    //Get user and its name
    @PreAuthorize("hasAuthority('ROLE_STAFF')")
    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @PreAuthorize("hasAuthority('ROLE_STAFF')")
    @GetMapping("/{username}")
    public ResponseEntity<Object> getUserByUserName(@PathVariable String username){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUserName(username));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("message", e.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    @GetMapping("/name/{id}")
    public ResponseEntity<Object> getUserNameByID(@PathVariable String id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("name", userService.getFullNameByID(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("message", e.getMessage()));
        }
    }

    //Update user
    @PatchMapping()
    public ResponseEntity<Object> patchUser(@RequestBody Map<String,String> userPatchRequestBody){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userPatchRequestBody));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("message", e.getMessage()));
        }
    }

}
