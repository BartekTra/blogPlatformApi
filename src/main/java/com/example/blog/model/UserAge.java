package com.example.blog.model;

public class UserAge {
    private int age;

    UserAge(int age){
        if(age > 0 && age < 140){
            this.age = age;
        } else {
            throw new IllegalArgumentException("Invalid age provided");
        }
    }

}
