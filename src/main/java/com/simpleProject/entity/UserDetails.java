package com.simpleProject.entity;

import jakarta.persistence.Column;

import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserDetails {
//
//    id (serial, primary key)
//    name (varchar)
//    description (text)
//    created_at (timestamp)
//    is_active (boolean)
//    amount (numeric)
//    additional_details (jsonb)


 private  String id;

 private String name;

 private LocalDateTime createdAt;
 private String description;

 private Boolean isActive;
 private double amount;


 private Map<String, Object> additionalDetails;


}
