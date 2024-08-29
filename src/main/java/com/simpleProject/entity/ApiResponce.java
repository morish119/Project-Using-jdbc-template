package com.simpleProject.entity;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponce {
    private HttpStatus status;
    private String message;
}
