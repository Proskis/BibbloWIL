package com.mycompany.bibbliotekcaseWIL;

/**
 *
 * @author Jonathan Hedemalm
 */
public class Session {
    
    public static boolean isManager = false;
    public static boolean isStaff = false;
    public static boolean isUser = false;

    public static int staffID = -1;
    public static int userID = -1;
    public static String loggedInName = null;

    public static String staffName = null;
    public static String userName = null;

    public static void clear() {
        isManager = false;
        isStaff = false;
        isUser = false;

        staffID = -1;
        userID = -1;

        staffName = null;
        userName = null;
    }
}