package org.nearbyshops.communitylibrary.Globals;

import org.nearbyshops.communitylibrary.DAOs.*;

/**
 * Created by sumeet on 9/8/16.
 */
public class Globals {

    public static BookDAO bookDAO = new BookDAO();
    public static BookCategoryDAO bookCategoryDAO = new BookCategoryDAO();
    public static MemberDAO memberDAO = new MemberDAO();

    public static BookReviewDAO bookReviewDAO = new BookReviewDAO();

    public static FavoriteBookDAO favoriteBookDAO = new FavoriteBookDAO();


}
