package org.nearbyshops.communitylibrary.Globals;

import org.nearbyshops.communitylibrary.DAOs.*;
import org.nearbyshops.communitylibrary.Model.BookMeetup;

/**
 * Created by sumeet on 9/8/16.
 */
public class Globals {

    public static BookDAO bookDAO = new BookDAO();
    public static BookCategoryDAO bookCategoryDAO = new BookCategoryDAO();
    public static MemberDAO memberDAO = new MemberDAO();

    public static BookReviewDAO bookReviewDAO = new BookReviewDAO();

    public static FavoriteBookDAO favoriteBookDAO = new FavoriteBookDAO();
    public static BookMeetupDAO bookMeetupDAO = new BookMeetupDAO();


}
