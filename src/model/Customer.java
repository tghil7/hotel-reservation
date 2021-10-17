package model;
import java.util.*;
import java.util.regex.*;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    public Customer() {

    }

    public String toString(){
        return "First name : " + firstName + " Last Name: " + lastName + " Email address: " + email;
    }

    final String emailRegex = "^(.+)@(.+).(.+)$";
    Pattern pattern = Pattern.compile(emailRegex);



    public Customer(String firstName, String lastName, String email) throws IllegalArgumentException{
        checkEmailValidity(email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public boolean checkEmailValidity(String email){
        if (!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Invalid email format. Please try again");

        }
        else return true;
    }

    public String getFirstName(){
      return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail (String email){
        this.email = email;
    }

    public void setLastName (String lastName){
        this.lastName = lastName;
    }

    public void setFirstName (String firstName){
        this.firstName = firstName;
    }





}
