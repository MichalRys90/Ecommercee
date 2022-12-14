package com.kodilla.ecommercee.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ActivityDto {

    private Long userId;

    private LocalDateTime dateTime;

    private String message;
}
