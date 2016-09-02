package org.nearbyshops.communitylibrary.Globals;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.communitylibrary.DAOs.*;
import org.nearbyshops.communitylibrary.DAOsPrepared.*;
import org.nearbyshops.communitylibrary.JDBCContract;
import org.nearbyshops.communitylibrary.Model.BookMeetup;

/**
 * Created by sumeet on 9/8/16.
 */
public class Globals {


    private static HikariDataSource dataSource;

    public static BookDAOPrepared bookDAO = new BookDAOPrepared();
    public static BookCategoryDAOPrepared bookCategoryDAO = new BookCategoryDAOPrepared();
    public static BookMeetupDAOPrepared bookMeetupDAO = new BookMeetupDAOPrepared();
    public static BookReviewDAOPrepared bookReviewDAO = new BookReviewDAOPrepared();
    public static FavoriteBookDAOPrepared favoriteBookDAO = new FavoriteBookDAOPrepared();
    public static MemberDAOPrepared memberDAO = new MemberDAOPrepared();




    public static HikariDataSource getDataSource()
    {
        if(dataSource==null)
        {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(JDBCContract.CURRENT_CONNECTION_URL);
            config.setUsername(JDBCContract.CURRENT_USERNAME);
            config.setPassword(JDBCContract.CURRENT_PASSWORD);

            dataSource = new HikariDataSource(config);
        }


        return dataSource;
    }

}
