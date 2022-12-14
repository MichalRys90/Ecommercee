package com.kodilla.ecommercee.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "USER_ID", unique = true)
    private Long userId;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "ACCOUNT_BLOCKED")
    private Boolean isBlocked;

    @Column(name = "USERKEY")
    private String userKey;

    @Column(name = "PASSWORD")
    private String password;

    @JsonBackReference
    @OneToMany(targetEntity = Order.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JsonIgnoreProperties("users")
    private List<Order> users = new ArrayList<>();

    @OneToMany(targetEntity = Activity.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Activity> activities = new ArrayList<>();

    public User(String username, String firstName, String lastName, Boolean isBlocked, String userKey, List<Order> users) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isBlocked = isBlocked;
        this.userKey = userKey;
        this.users = users;
    }

    public User(String username, String firstName, String lastName, Boolean isBlocked, String userKey, List<Order> users, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isBlocked = isBlocked;
        this.userKey = userKey;
        this.users = users;
        this.password = password;
    }

    public User(Long userId, String username, String firstName, String lastName, Boolean isBlocked, String userKey, String password, List<Order> users) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isBlocked = isBlocked;
        this.userKey = userKey;
        this.users = users;
        this.password = password;
    }
}
