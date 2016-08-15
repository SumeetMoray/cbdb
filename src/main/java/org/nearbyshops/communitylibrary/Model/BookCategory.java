package org.nearbyshops.communitylibrary.Model;

/**
 * Created by sumeet on 8/8/16.
 */
public class BookCategory {

    // Table Name
    public static final String TABLE_NAME = "BOOK_CATEGORY";

    // column Names
    public static final String BOOK_CATEGORY_ID = "BOOK_CATEGORY_ID";
    public static final String BOOK_CATEGORY_NAME = "BOOK_CATEGORY_NAME";
    public static final String IMAGE_URL = "IMAGE_URL";
    public static final String BACKDROP_IMAGE_URL = "BACKDROP_IMAGE_URL";
    public static final String PARENT_CATEGORY_ID = "PARENT_CATEGORY_ID";


    // CreateTableStatement
    public static final String createTableBookCategoryPostgres = "CREATE TABLE IF NOT EXISTS "
            + BookCategory.TABLE_NAME + "("

            + " " + BookCategory.BOOK_CATEGORY_ID + " SERIAL PRIMARY KEY,"
            + " " + BookCategory.BOOK_CATEGORY_NAME + " VARCHAR(100),"
            + " " + BookCategory.IMAGE_URL + " VARCHAR(100),"
            + " " + BookCategory.BACKDROP_IMAGE_URL + " VARCHAR(100),"
            + " " + BookCategory.PARENT_CATEGORY_ID + " INT,"

            + " FOREIGN KEY(" + BookCategory.PARENT_CATEGORY_ID +") REFERENCES "
            + BookCategory.TABLE_NAME + "(" + BookCategory.BOOK_CATEGORY_ID + ")"
            + ")";


    // Instance Variables

    private Integer bookCategoryID;
    private String bookCategoryName;
    private String imageURL;
    private String backdropImageURL;
    private Integer parentCategoryID;
    private Integer rt_book_count; // Number of books in this book category

    // Getter and Setters


    public Integer getRt_book_count() {
        return rt_book_count;
    }

    public void setRt_book_count(Integer rt_book_count) {
        this.rt_book_count = rt_book_count;
    }

    public Integer getBookCategoryID() {
        return bookCategoryID;
    }

    public void setBookCategoryID(Integer bookCategoryID) {
        this.bookCategoryID = bookCategoryID;
    }

    public String getBookCategoryName() {
        return bookCategoryName;
    }

    public void setBookCategoryName(String bookCategoryName) {
        this.bookCategoryName = bookCategoryName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getBackdropImageURL() {
        return backdropImageURL;
    }

    public void setBackdropImageURL(String backdropImageURL) {
        this.backdropImageURL = backdropImageURL;
    }

    public Integer getParentCategoryID() {
        return parentCategoryID;
    }

    public void setParentCategoryID(Integer parentCategoryID) {
        this.parentCategoryID = parentCategoryID;
    }
}
