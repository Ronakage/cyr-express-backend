package com.ronymawad.cyrexpress.shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.ronymawad.cyrexpress.shop.exception.RoleNameNotFound;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("user")
public class UserModel implements UserDetails {
    @Id
    @Setter(AccessLevel.NONE)
    private String id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    @Indexed(unique = true)
    private String email;
    @NonNull
    @JsonIgnore
    private String password;
    @NonNull
    @Setter(AccessLevel.NONE)
    private String roles;

    @DBRef
    private List<OrderModel> orders;
    private List<ComplaintModel> complaints;

    private String shopName;
    private String shopAddress;

    private boolean isOnline;
    private boolean isWorking;
    private boolean isValidated;


    private LocalDateTime timeCreated;
    private LocalDateTime timeModified;
    @JsonIgnore
    private LocalDateTime timeDisactived;
    @JsonIgnore
    private LocalDateTime restrictionStartDate;
    private LocalDateTime restrictionEndDate;

    public static String initiateRole(String roles) throws RoleNameNotFound {
        switch(roles.toLowerCase()){
            case "admin":
                return "ROLE_ADMIN,ROLE_STAFF,ROLE_SHOP_OWNER,ROLE_DRIVER,ROLE_CLIENT";
            case "staff":
                return "ROLE_STAFF,ROLE_SHOP_OWNER,ROLE_DRIVER,CLIENT";
            case "shop":
            case "shop_owner":
            case "owner":
                return "ROLE_SHOP_OWNER,ROLE_CLIENT";
            case "driver":
                return "ROLE_DRIVER,ROLE_CLIENT";
            case "client":
            case "user":
                return "ROLE_CLIENT";
            default:
                throw new RoleNameNotFound(roles);
        }
    }
    public String getFullName(){
        if(!this.roles.matches("ROLE_SHOP_OWNER,ROLE_CLIENT")) return this.firstName + " " + this.lastName;
        else return this.shopName;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    //Setup locking and restricting
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
