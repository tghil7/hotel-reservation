package model;
import java.util.*;
import java.util.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    public String toString(){
        return "First name : " + firstName + "Last Name: " + lastName;
    }

    String emailRegex = "^(.+)@(.+).(.+)$";
    Pattern pattern = Pattern.compile(emailRegex);

    Matcher matcher = pattern.matcher("jeff@example.com")
}
