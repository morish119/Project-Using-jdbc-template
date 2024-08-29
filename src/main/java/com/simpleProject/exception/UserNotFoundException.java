package com.simpleProject.exception;

public class UserNotFoundException  extends RuntimeException {
    public  UserNotFoundException (){
        super("Resource not found !!!!");
    }
    public UserNotFoundException (String s){
        super(s);
    }

}
