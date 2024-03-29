package org.nearbyshops.communitylibrary.Model;

import java.sql.Timestamp;

/**
 * Created by sumeet on 8/8/16.
 */
public class Member {

    // Table Name
    public static final String TABLE_NAME = "MEMBER";

    // column Names
    public static final String MEMBER_ID = "MEMBER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String MEMBER_NAME = "MEMBER_NAME";
    public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";
    public static final String CITY = "CITY";
    public static final String ABOUT = "ABOUT";
    public static final String DATE_OF_BIRTH = "DATE_OF_BIRTH";


    // create table Statement
    public static final String createTableMemberPostgres =

            "CREATE TABLE IF NOT EXISTS " + Member.TABLE_NAME + "("

                    + " " + Member.MEMBER_ID + " SERIAL PRIMARY KEY,"
                    + " " + Member.USER_NAME + " text,"
                    + " " + Member.PASSWORD + " text,"
                    + " " + Member.MEMBER_NAME + " text,"
                    + " " + Member.PROFILE_IMAGE_URL + " text,"
                    + " " + Member.CITY + " text,"
                    + " " + Member.ABOUT + " text,"
                    + " " + Member.DATE_OF_BIRTH + " timestamp with time zone,"
                    + " UNIQUE (" + Member.USER_NAME + ")"

                    + ")";


    // Instance Variables

    private Integer memberID;
    private String userName;
    private String password;
    private String memberName;
    private String profileImageURL;
    private String city;
    private String about;
    private Timestamp dateOfBirth;


    // Getter and Setter Methods


    public Integer getMemberID() {
        return memberID;
    }

    public void setMemberID(Integer memberID) {
        this.memberID = memberID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }


    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
