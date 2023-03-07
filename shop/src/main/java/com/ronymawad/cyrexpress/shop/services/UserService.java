package com.ronymawad.cyrexpress.shop.services;

import com.ronymawad.cyrexpress.shop.exception.EmailAlreadyUsedException;
import com.ronymawad.cyrexpress.shop.exception.RoleNameNotFound;
import com.ronymawad.cyrexpress.shop.exception.UserNotFound;
import com.ronymawad.cyrexpress.shop.exception.WrongPasswordException;
import com.ronymawad.cyrexpress.shop.models.UserModel;
import com.ronymawad.cyrexpress.shop.repositories.UserRepository;
import com.ronymawad.cyrexpress.shop.request.SignUpUserRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public record UserService(UserRepository userRepository) {

    public void signUpUser(SignUpUserRequest request) throws EmailAlreadyUsedException, RoleNameNotFound {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(userRepository.existsByEmail(request.email())){
            throw new EmailAlreadyUsedException(request.email());
        }
        else{
            UserModel user = UserModel
                    .builder()
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .email(request.email())
                    .password(encoder.encode(request.password()))
                    .roles(UserModel.initiateRole((request.roles())))
                    .timeCreated(LocalDateTime.now())
                    .build();
            //Do registration checks
            userRepository.save(user);
        }
    }

    public UserModel signInUser(Authentication authentication) throws UserNotFound {
        UserModel user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UserNotFound(authentication.getName()));
        return user;
    }

    public UserModel logOutUser(Authentication authentication) throws UserNotFound {
        UserModel user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UserNotFound(authentication.getName()));
        user.setWorking(false);
        user.setOnline(false);
        return user;
    }

    public UserModel changeWorkingStatus(Authentication authentication) throws UserNotFound {
        UserModel user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UserNotFound(authentication.getName()));
        user.setWorking(true);
        userRepository.save(user);
        return user;
    }

    public List<UserModel> getAllUsers(){
        return userRepository.findAll();
    }

    public UserModel getUserByUserName(String username) throws Exception {
        if(userRepository.existsById(username)){
            return userRepository.findById(username).orElseThrow(() -> new Exception("User wasn't found by ID."));
        }
        else if(userRepository.existsByEmail(username)){
            return userRepository.findByEmail(username).orElseThrow(() -> new Exception("User wasn't found by E-Mail."));
        }
        else{throw new Exception("User wasn't found by ID or E-Mail.");}
    }

    public String getFullNameByID(String id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("Invalid User ID.")).getFullName();
    }

    public UserModel updateUser(Map<String, String> body) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!body.containsKey("id") || !body.containsKey("password")) throw new Exception("Invalid Update Request.");
        UserModel user = userRepository.findById(body.get("id")).orElseThrow(() -> new Exception("Invalid User ID."));
        if(user.getRoles().contains("ROLE_ADIMN") || user.getRoles().contains("ROLE_STAFF")) throw new Exception("Invalid Update Request. Role Access Denied.");
        if(!encoder.matches(body.get("password"), user.getPassword())) throw new WrongPasswordException();
        if(body.containsKey("newFirstName")){
            user.setFirstName(body.get("newFirstName"));
        }
        else if(body.containsKey("newLastName")){
            user.setLastName(body.get("newLastName"));
        }
        else if(body.containsKey("newEmail")){
            user.setEmail(body.get("newEmail"));
        }
        else if(body.containsKey("newPassword")){
            user.setPassword(encoder.encode(body.get("newPassword")));
        }
        else throw new Exception("No fields to be updated.");
        user.setTimeModified(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

}
