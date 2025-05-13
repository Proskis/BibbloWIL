package com.mycompany.bibbliotekcaseWIL;

/**
 *
 * @author Jonathan Hedemalm
 */
public class Staff {
    private int staffID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String username;
    private String password;
    
   public Staff(int staffID, String firstName, String lastName, String email, String phoneNo, String userName, String password) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.username = userName;
        this.password = password;

    }
    
    public int getStaffID() {return staffID;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getEmail() {return email; }
    public String getPhoneNo() {return phoneNo;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    
    @Override
    public String toString() {return getLastName() + ", " + getFirstName();} 
 
}