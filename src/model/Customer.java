package model;
import java.util.*;
import java.util.regex.*;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    public String toString(){
        return "First name : " + firstName + "Last Name: " + lastName;
    }

    String emailRegex = "^(.+)@(.+).(.+)$";
    Pattern pattern = Pattern.compile(emailRegex);


    public Customer(String firstName,String lastName, String email) throws IllegalArgumentException{
        if (!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Invalid email format. Please try again");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
