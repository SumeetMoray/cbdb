package org.nearbyshops.communitylibrary.Model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by sumeet on 8/8/16.
 */
public class BookReview {


    // Table Name
    public static final String TABLE_NAME = "BOOK_REVIEW";

    // column Names
    public static final String BOOK_REVIEW_ID = "BOOK_REVIEW_ID";
    public static final String BOOK_ID = "BOOK_ID";
    public static final String MEMBER_ID = "MEMBER_ID";
    public static final String RATING = "RATING";
    public static final String REVIEW_TEXT = "REVIEW_TEXT";
    public static final String REVIEW_DATE = "REVIEW_DATE";
    public static final String REVIEW_TITLE = "REVIEW_TITLE";

    // review_date, title


    // create Table statement
    public static final String createTableBookReviewPostgres =

            "CREATE TABLE IF NOT EXISTS " + BookReview.TABLE_NAME + "("

            + " " + BookReview.BOOK_REVIEW_ID + " SERIAL PRIMARY KEY,"
            + " " + BookReview.BOOK_ID + " INT,"
            + " " + BookReview.MEMBER_ID + " INT,"
            + " " + BookReview.RATING + " INT,"
            + " " + BookReview.REVIEW_TEXT + " VARCHAR(10000),"
            + " " + BookReview.REVIEW_TITLE + " VARCHAR(1000),"
            + " " + BookReview.REVIEW_DATE + "  timestamp with time zone NOT NULL DEFAULT now(),"

            + " FOREIGN KEY(" + BookReview.BOOK_ID +") REFERENCES " + Book.TABLE_NAME + "(" + Book.BOOK_ID + "),"
            + " FOREIGN KEY(" + BookReview.MEMBER_ID +") REFERENCES " + Member.TABLE_NAME + "(" + Member.MEMBER_ID + "),"
            + " UNIQUE (" + BookReview.BOOK_ID + "," + BookReview.MEMBER_ID + ")"
            + ")";



    // Instance Variables

    private Integer bookReviewID;
    private Integer bookID;
    private Integer memberID;
    private Integer rating;
    private String reviewText;
    private String reviewTitle;
    private Timestamp reviewDate;

    private Member rt_member_profile;


    // getter and Setter Methods


    public Integer getBookReviewID() {
        return bookReviewID;
    }

    public void setBookReviewID(Integer bookReviewID) {
        this.bookReviewID = bookReviewID;
    }

    public Integer getBookID() {
        return bookID;
    }

    public void setBookID(Integer bookID) {
        this.bookID = bookID;
    }

    public Integer getMemberID() {
        return memberID;
    }

    public void setMemberID(Integer memberID) {
        this.memberID = memberID;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }


    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }


    public Timestamp getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Timestamp reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Member getRt_member_profile() {
        return rt_member_profile;
    }

    public void setRt_member_profile(Member rt_member_profile) {
        this.rt_member_profile = rt_member_profile;
    }
}
