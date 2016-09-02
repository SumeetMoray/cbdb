package org.nearbyshops.communitylibrary.DAOsPrepared;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.communitylibrary.Globals.Globals;
import org.nearbyshops.communitylibrary.JDBCContract;
import org.nearbyshops.communitylibrary.Model.Book;
import org.nearbyshops.communitylibrary.Model.BookMeetup;
import org.nearbyshops.communitylibrary.ModelEndpoint.BookMeetupEndpoint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sumeet on 8/8/16.
 */
public class BookMeetupDAOPrepared {


    HikariDataSource dataSource = Globals.getDataSource();

        @Override
        protected void finalize() throws Throwable {
            // TODO Auto-generated method stub
            super.finalize();
        }


        public int saveBookMeetup(BookMeetup bookMeetup)
        {


            Connection conn = null;
            PreparedStatement statement = null;
            int idOfInsertedRow = 0;


            String insertStatement = "INSERT INTO "
                    + BookMeetup.TABLE_NAME
                    + "("

                    + BookMeetup.VENUE + ","
                    + BookMeetup.LONGITUDE + ","
                    + BookMeetup.LATITUDE + ","

                    + BookMeetup.MEETUP_NAME + ","
                    + BookMeetup.MEETUP_PURPOSE + ","
                    + BookMeetup.POSTER_URL + ","

                    + BookMeetup.DATE_AND_TIME + ""

                    + ") VALUES(?,?,?, ?,?,? ,?)";

            try {
                conn = dataSource.getConnection();

                statement = conn.prepareStatement(insertStatement,PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setString(1,bookMeetup.getVenue());
                statement.setDouble(2,bookMeetup.getLongitude());
                statement.setDouble(3,bookMeetup.getLatitude());
                statement.setString(4,bookMeetup.getMeetupName());
                statement.setString(5,bookMeetup.getMeetupPurpose());
                statement.setString(6,bookMeetup.getPosterURL());
                statement.setTimestamp(7,bookMeetup.getDateAndTime());

                idOfInsertedRow = statement.executeUpdate();

                ResultSet rs = statement.getGeneratedKeys();

                if(rs.next())
                {
                    idOfInsertedRow = rs.getInt(1);
                }


            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            finally
            {

                try {

                    if(statement!=null)
                    {statement.close();}

                }
                catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {

                    if(conn!=null)
                    {conn.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return idOfInsertedRow;

        }


        public int updateBookMeetup(BookMeetup bookMeetup)
        {

            //,int itemCategoryID

            //item.setItemCategoryID(itemCategoryID);

            String updateStatement = "UPDATE " + BookMeetup.TABLE_NAME
                    + " SET "
                    + BookMeetup.VENUE + " = ?,"
                    + BookMeetup.LONGITUDE + " = ?,"
                    + BookMeetup.LATITUDE + " = ?,"

                    + BookMeetup.MEETUP_NAME + " = ?,"
                    + BookMeetup.MEETUP_PURPOSE + " = ?,"
                    + BookMeetup.POSTER_URL + " = ?,"

                    + BookMeetup.DATE_AND_TIME + " = ?";


            updateStatement = updateStatement + " WHERE "
                    + BookMeetup.BOOK_MEETUP_ID + " = ?" ;

            Connection conn = null;
            PreparedStatement statement = null;

            int rowCountUpdated = 0;

            try {

                conn = dataSource.getConnection();
                statement = conn.prepareStatement(updateStatement);

                statement.setString(1,bookMeetup.getVenue());
                statement.setDouble(2,bookMeetup.getLongitude());
                statement.setDouble(3,bookMeetup.getLatitude());

                statement.setString(4,bookMeetup.getMeetupName());
                statement.setString(5,bookMeetup.getMeetupPurpose());
                statement.setString(6,bookMeetup.getPosterURL());

                statement.setTimestamp(7,bookMeetup.getDateAndTime());
                statement.setInt(8,bookMeetup.getBookMeetupID());

                rowCountUpdated = statement.executeUpdate();
                System.out.println("Total rows updated: " + rowCountUpdated);


            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally

            {

                try {

                    if(statement!=null)
                    {statement.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {

                    if(conn!=null)
                    {conn.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return rowCountUpdated;
        }



        public int deleteBookMeetp(int bookMeetupID)
        {

            String deleteStatement = "DELETE FROM " + BookMeetup.TABLE_NAME
                    + " WHERE " + BookMeetup.BOOK_MEETUP_ID + " = ?";

            Connection conn= null;
            PreparedStatement statement = null;
            int rowCountDeleted = 0;
            try {

                conn = dataSource.getConnection();

                statement = conn.prepareStatement(deleteStatement);
                statement.setInt(1,bookMeetupID);

                rowCountDeleted = statement.executeUpdate();
                System.out.println("Rows Deleted: " + rowCountDeleted);


            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            finally

            {

                try {

                    if(statement!=null)
                    {statement.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {

                    if(conn!=null)
                    {conn.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return rowCountDeleted;
        }





        public List<BookMeetup> getBookMeetup(
                Double memberLongitude,
                Double memberLatitude,
                Double proximityMaximum,
                String searchString,
                String sortBy,
                Integer limit, Integer offset
        ) {

            boolean isFirst = true;

            String query = "";

            String queryNormal = "SELECT * FROM " + Book.TABLE_NAME;


            String queryJoin = "SELECT "

                    + " 6371 * acos(cos( radians("
                    + memberLatitude + ")) * cos( radians( " + BookMeetup.LATITUDE + ")) * cos(radians( "
                    + BookMeetup.LONGITUDE + ") - radians(" + memberLongitude + "))"
                    + " + sin( radians(" + memberLatitude + ")) * sin(radians(" + BookMeetup.LATITUDE + "))) as distance" + ","

                    + BookMeetup.TABLE_NAME + "." + BookMeetup.BOOK_MEETUP_ID + ","
                    + BookMeetup.TABLE_NAME + "." + BookMeetup.VENUE + ","
                    + BookMeetup.TABLE_NAME + "." + BookMeetup.LONGITUDE + ","
                    + BookMeetup.TABLE_NAME + "." + BookMeetup.LATITUDE + ","
                    + BookMeetup.TABLE_NAME + "." + BookMeetup.MEETUP_NAME+ ","
                    + BookMeetup.TABLE_NAME + "." + BookMeetup.MEETUP_PURPOSE + ","
                    + BookMeetup.TABLE_NAME + "." + BookMeetup.POSTER_URL + ","
                    + BookMeetup.TABLE_NAME + "." + BookMeetup.DATE_AND_TIME + ""

                    + " FROM "
                    + BookMeetup.TABLE_NAME;




            // Proximity Filter
            if(proximityMaximum != null && memberLatitude != null && memberLongitude != null)
            {
                // proximity > 0 && (deliveryRangeMax==0 || (deliveryRangeMax > 0 && proximity <= deliveryRangeMax))

                String queryPartProximity = "";

                // Make sure that shop center lies between the bounding coordinates generated by proximity bounding box


                // filter using Haversine formula using SQL math functions
                queryPartProximity = queryPartProximity
                        + " (6371.01 * acos(cos( radians("
                        + memberLatitude
                        + ")) * cos( radians("
                        + BookMeetup.LATITUDE
                        + " )) * cos(radians( "
                        + BookMeetup.LONGITUDE
                        + ") - radians("
                        + memberLongitude
                        + "))"
                        + " + sin( radians("
                        + memberLatitude
                        + ")) * sin(radians("
                        + BookMeetup.LATITUDE
                        + ")))) <= "
                        + proximityMaximum ;


                queryNormal = queryNormal + " WHERE ";
                queryJoin = queryJoin + " WHERE ";

                // reset the flag

                isFirst = false;


                queryNormal = queryNormal + queryPartProximity;

                queryJoin = queryJoin + queryPartProximity;



            }


            if(searchString !=null)
            {
                String queryPartSearch = BookMeetup.TABLE_NAME + "." + BookMeetup.MEETUP_NAME +" ilike '%" + searchString + "%'"
                        + " or " + BookMeetup.TABLE_NAME + "." + BookMeetup.MEETUP_PURPOSE + " ilike '%" + searchString + "%'"
                        + " or " + BookMeetup.TABLE_NAME + "." + BookMeetup.VENUE + " ilike '%" + searchString + "%'";



                if(isFirst)
                {
                    queryJoin = queryJoin + " WHERE " + queryPartSearch;
                    queryNormal = queryNormal + " WHERE " + queryPartSearch;

                    isFirst = false;
                }
                else
                {

                    queryJoin = queryJoin + " AND " + queryPartSearch;
                    queryNormal = queryNormal + " AND " + queryPartSearch;
                }
            }



            // Applying filters



            if(sortBy!=null)
            {
                if(!sortBy.equals(""))
                {
                    String queryPartSortBy = " ORDER BY " + sortBy;

                    queryNormal = queryNormal + queryPartSortBy;
                    queryJoin = queryJoin + queryPartSortBy;
                }
            }



            if(limit != null)
            {

                String queryPartLimitOffset = "";

                if(offset>0)
                {
                    queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

                }else
                {
                    queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
                }


                queryNormal = queryNormal + queryPartLimitOffset;
                queryJoin = queryJoin + queryPartLimitOffset;
            }






		/*
		Applying filters Ends
		 */



            query = queryJoin;


            System.out.println(queryJoin);


            /*

            if(bookCategoryID!=null)
            {
                query = queryJoin;
                isJoinQuery = true;

            }else
            {
                query = queryNormal;
            }

            */



            ArrayList<BookMeetup> bookList = new ArrayList<BookMeetup>();


            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {

                conn = dataSource.getConnection();
                stmt = conn.createStatement();

                rs = stmt.executeQuery(query);

                while(rs.next())
                {

                    BookMeetup bookMeetup = new BookMeetup();

                    bookMeetup.setBookMeetupID(rs.getInt(BookMeetup.BOOK_MEETUP_ID));
                    bookMeetup.setVenue(rs.getString(BookMeetup.VENUE));
                    bookMeetup.setLongitude(rs.getDouble(BookMeetup.LONGITUDE));
                    bookMeetup.setLatitude(rs.getDouble(BookMeetup.LATITUDE));
                    bookMeetup.setMeetupName(rs.getString(BookMeetup.MEETUP_NAME));
                    bookMeetup.setMeetupPurpose(rs.getString(BookMeetup.MEETUP_PURPOSE));
                    bookMeetup.setPosterURL(rs.getString(BookMeetup.POSTER_URL));
                    bookMeetup.setDateAndTime(rs.getTimestamp(BookMeetup.DATE_AND_TIME));

                    bookMeetup.setRt_distance(rs.getDouble("distance"));

                    bookList.add(bookMeetup);
                }



                System.out.println("bookMeetups By ID " + bookList.size());

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            finally

            {

                try {
                    if(rs!=null)
                    {rs.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {

                    if(stmt!=null)
                    {stmt.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {

                    if(conn!=null)
                    {conn.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return bookList;
        }



        public BookMeetupEndpoint getEndPointMetadata(
                Double memberLongitude,
                Double memberLatitude,
                Double proximityMaximum)
        {

            boolean isFirst = true;

            String query = "";

            String queryNormal = "SELECT "
                    + "count(*) as item_count" + ""
                    + " FROM " + BookMeetup.TABLE_NAME;




            // Proximity Filter
            if(proximityMaximum != null && memberLatitude != null && memberLongitude != null)
            {
                // proximity > 0 && (deliveryRangeMax==0 || (deliveryRangeMax > 0 && proximity <= deliveryRangeMax))

                String queryPartProximity = "";

                // Make sure that shop center lies between the bounding coordinates generated by proximity bounding box


                // filter using Haversine formula using SQL math functions
                queryPartProximity = queryPartProximity
                        + " (6371.01 * acos(cos( radians("
                        + memberLatitude
                        + ")) * cos( radians("
                        + BookMeetup.LATITUDE
                        + " )) * cos(radians( "
                        + BookMeetup.LONGITUDE
                        + ") - radians("
                        + memberLongitude
                        + "))"
                        + " + sin( radians("
                        + memberLatitude
                        + ")) * sin(radians("
                        + BookMeetup.LATITUDE
                        + ")))) <= "
                        + proximityMaximum ;


                if(isFirst)
                {
                    queryNormal = queryNormal + " WHERE ";

                    // reset the flag
                    isFirst = false;

                }else
                {
                    queryNormal = queryNormal + " AND ";
                }


                queryNormal = queryNormal + queryPartProximity;
            }





		/*
		Applying filters Ends
		 */

            query = queryNormal;


            BookMeetupEndpoint endPoint = new BookMeetupEndpoint();


            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {


                conn = dataSource.getConnection();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

                while(rs.next())
                {
                    endPoint.setItemCount(rs.getInt("item_count"));

                }




                System.out.println("Item Count : " + endPoint.getItemCount());


            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            finally

            {

                try {
                    if(rs!=null)
                    {rs.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {

                    if(stmt!=null)
                    {stmt.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {

                    if(conn!=null)
                    {conn.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return endPoint;
        }



        public BookMeetup getBookMeetup(int bookMeetupID)
        {

            String query = "SELECT * FROM " +  BookMeetup.TABLE_NAME
                    + " WHERE " + BookMeetup.BOOK_MEETUP_ID + " = " + bookMeetupID;


            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;


            //ItemCategory itemCategory = new ItemCategory();
            BookMeetup bookMeetup = null;

            try {


                conn = dataSource.getConnection();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);



                while(rs.next())
                {
                    bookMeetup = new BookMeetup();

                    bookMeetup.setBookMeetupID(rs.getInt(BookMeetup.BOOK_MEETUP_ID));
                    bookMeetup.setVenue(rs.getString(BookMeetup.VENUE));
                    bookMeetup.setLongitude(rs.getDouble(BookMeetup.LONGITUDE));
                    bookMeetup.setLatitude(rs.getDouble(BookMeetup.LATITUDE));
                    bookMeetup.setMeetupName(rs.getString(BookMeetup.MEETUP_NAME));
                    bookMeetup.setMeetupPurpose(rs.getString(BookMeetup.MEETUP_PURPOSE));
                    bookMeetup.setPosterURL(rs.getString(BookMeetup.POSTER_URL));
                    bookMeetup.setDateAndTime(rs.getTimestamp(BookMeetup.DATE_AND_TIME));

                    System.out.println("Get BookMeetup by ID : " + bookMeetup.getBookMeetupID());
                }

                //System.out.println("Total itemCategories queried " + itemCategoryList.size());



            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally

            {

                try {
                    if(rs!=null)
                    {rs.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {

                    if(stmt!=null)
                    {stmt.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {

                    if(conn!=null)
                    {conn.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return bookMeetup;
        }
}
