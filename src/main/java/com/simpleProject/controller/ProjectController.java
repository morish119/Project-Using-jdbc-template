package com.simpleProject.controller;

import com.simpleProject.entity.UserDetails;
import com.simpleProject.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {
    @Autowired
   private DetailsService detailsService;


    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDetails userDetails){
          detailsService.saveUser(userDetails);
          return new ResponseEntity<>(userDetails, HttpStatus.CREATED);
    }
     @PostMapping ("/delete/{id}")
     public  ResponseEntity<?> deleteUser(@PathVariable String id){

        detailsService.deleteUser(id);
        return new ResponseEntity<>("Your id is Deleted",HttpStatus.ACCEPTED);
    }

    @PostMapping("/alluser")
    public  ResponseEntity<?> getAllUser(){
        List<UserDetails> allUserDetails = detailsService.getAllUserDetails();
        return new ResponseEntity<>(allUserDetails,HttpStatus.FOUND);

    }

    @PostMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id){

        String userIDById = detailsService.getUserIDById(id).toString();
        return new ResponseEntity<>(userIDById,HttpStatus.FOUND);

    }
    @PostMapping("update/{id}")
    public ResponseEntity<String> updateEntity(@PathVariable String id, @RequestBody UserDetails userDetails) {
        userDetails.setId(id); // Set the ID for the entity to be updated
        int result = detailsService.updateEntity(id,userDetails);
        if (result > 0) {
            return new ResponseEntity<>("Entity updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update entity", HttpStatus.BAD_REQUEST);
        }
    }





}
