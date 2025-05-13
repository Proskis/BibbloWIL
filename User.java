
package com.mycompany.bibbliotekcaseWIL;

/**
 *
 * @author Jonathan Hedemalm
 */
public class User {
    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String username;
    private String password;
    private int userTypeID;
    
    /**
     * 
     * @param userID
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNo
     * @param userTypeID
     * @param userName
     * @param password
     */
    public User(int userID, String firstName, String lastName, String email, String phoneNo, String userName, String password, int userTypeID) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.username = userName;
        this.password = password;
        this.userTypeID = userTypeID;

    }
    
    public int getUserID() {return userID;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getEmail() {return email; }
    public String getPhoneNo() {return phoneNo;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public int getUserTypeID() {return userTypeID;}
    
    @Override
    public String toString() {return getLastName() + ", " + getFirstName();} 
    
}