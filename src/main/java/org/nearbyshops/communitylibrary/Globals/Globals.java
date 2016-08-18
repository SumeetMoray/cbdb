package org.nearbyshops.communitylibrary.Globals;

import org.nearbyshops.communitylibrary.DAOs.BookCategoryDAO;
import org.nearbyshops.communitylibrary.DAOs.BookDAO;
import org.nearbyshops.communitylibrary.DAOs.BookReviewDAO;
import org.nearbyshops.communitylibrary.DAOs.MemberDAO;

/**
 * Created by sumeet on 9/8/16.
 */
public class Globals {

    public static BookDAO bookDAO = new BookDAO();
    public static BookCategoryDAO bookCategoryDAO = new BookCategoryDAO();
    public static MemberDAO memberDAO = new MemberDAO();

    public static BookReviewDAO bookReviewDAO = new BookReviewDAO();


}
