package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.exception.CartNotFoundException;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "ACTIVITIES")
public class Activity {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @NotNull
    @Column(name = "DATE_TIME")
    private LocalDateTime dateTime;

    @NotNull
    @Column(name = "MESSAGE", length = 1000)
    private String message;

    public Activity(User user, String message) {
        this.user = user;
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }

}
