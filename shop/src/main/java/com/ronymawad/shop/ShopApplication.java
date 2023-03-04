package com.ronymawad.shop;

import com.ronymawad.shop.repositories.OrderRepository;
import com.ronymawad.shop.repositories.UserRepository;
import com.ronymawad.shop.request.SignUpUserRequest;
import com.ronymawad.shop.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {UserRepository.class, OrderRepository.class})
public class ShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserService userService, MongoTemplate mongoTemplate){
        return args -> {
            SignUpUserRequest urr1 = new SignUpUserRequest(
                    "Rony",
                    "Mawad",
                    "rm@gmail.com",
                    "rm123",
                    "admin"
            );
            SignUpUserRequest urr2 = new SignUpUserRequest(
                    "Chady",
                    "Ollaik",
                    "co@gmail.com",
                    "co123",
                    "driver"
            );
            SignUpUserRequest urr3 = new SignUpUserRequest(
                    "Baher",
                    "Khader",
                    "bk@gmail.com",
                    "bk123",
                    "owner"
            );
            SignUpUserRequest urr4 = new SignUpUserRequest(
                    "Ahmad",
                    "Abdulsalam",
                    "aa@gmail.com",
                    "aa123",
                    "staff"
            );
            try{
                userService.signUpUser(urr1);
                userService.signUpUser(urr2);
                userService.signUpUser(urr3);
                userService.signUpUser(urr4);
            } catch (Exception e){}
        };
    }
}
