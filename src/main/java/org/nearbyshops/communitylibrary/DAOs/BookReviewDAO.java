package org.nearbyshops.communitylibrary.DAOs;

import org.nearbyshops.communitylibrary.JDBCContract;
import org.nearbyshops.communitylibrary.Model.Book;
import org.nearbyshops.communitylibrary.Model.BookReview;
import org.nearbyshops.communitylibrary.ModelEndpoint.BookReviewEndpoint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sumeet on 8/8/16.
 */
public class BookReviewDAO {


        @Override
        protected void finalize() throws Throwable {
            // TODO Auto-generated method stub
            super.finalize();
        }


        public int saveBookReview(BookReview bookReview)
        {


            Connection conn = null;
            Statement stmt = null;
            int idOfInsertedRow = 0;



            String insertStatement = "INSERT INTO "
                    + BookReview.TABLE_NAME
                    + "("
                    + BookReview.BOOK_ID + ","
                    + BookReview.MEMBER_ID + ","
                    + BookReview.RATING + ","
                    + BookReview.REVIEW_TEXT + ","
                    + BookReview.REVIEW_DATE + ","
                    + BookReview.REVIEW_TITLE + ""


                    + ") VALUES("
                    + "" + bookReview.getBookID() + ","
                    + "" + bookReview.getMemberID() + ","
                    + "" + bookReview.getRating() + ","
                    + "'" + bookReview.getReviewText() + "',"
                    + "" + " now() " + ","
                    + "'" + bookReview.getReviewTitle() + "'"
                    + ")";

            try {

                conn = DriverManager.getConnection(
                        JDBCContract.CURRENT_CONNECTION_URL,
                        JDBCContract.CURRENT_USERNAME,
                        JDBCContract.CURRENT_PASSWORD);

                stmt = conn.createStatement();

                idOfInsertedRow = stmt.executeUpdate(insertStatement,Statement.RETURN_GENERATED_KEYS);

                ResultSet rs = stmt.getGeneratedKeys();

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

                    if(stmt!=null)
                    {stmt.close();}

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


        public int updateBookReview(BookReview bookReview)
        {

            //,int itemCategoryID

            //item.setItemCategoryID(itemCategoryID);

            String updateStatement = "UPDATE " + BookReview.TABLE_NAME

                    + " SET "

                    + BookReview.BOOK_ID + " = " + "" + bookReview.getBookID() + "" + ","
                    + BookReview.MEMBER_ID + " = " + "" + bookReview.getMemberID() + "" + ","
                    + BookReview.RATING + " = " + "" + bookReview.getRating() + "" + ","
                    + BookReview.REVIEW_TEXT + " = " + "'" + bookReview.getReviewText() + "'" + ","
                    + BookReview.REVIEW_TITLE + " = " + "'" + bookReview.getReviewTitle() + "'" + ""


                    + " WHERE "
                    + BookReview.BOOK_REVIEW_ID + " = " + bookReview.getBookReviewID();

            //+ BookReview.REVIEW_DATE + " = " + "'" + bookReview.getReviewDate() + "'" + ","

            Connection conn = null;
            Statement stmt = null;

            int rowCountUpdated = 0;

            try {

                conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
                        JDBCContract.CURRENT_USERNAME,
                        JDBCContract.CURRENT_PASSWORD);

                stmt = conn.createStatement();

                rowCountUpdated = stmt.executeUpdate(updateStatement);


                System.out.println("Total rows updated: " + rowCountUpdated);


            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally

            {

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

            return rowCountUpdated;
        }



        public int deleteBookReview(int bookReviewID)
        {

            String deleteStatement = "DELETE FROM " + BookReview.TABLE_NAME
                    + " WHERE " + BookReview.BOOK_REVIEW_ID + " = " + bookReviewID;

            Connection conn= null;
            Statement stmt = null;
            int rowCountDeleted = 0;
            try {

                conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
                        JDBCContract.CURRENT_USERNAME,
                        JDBCContract.CURRENT_PASSWORD);

                stmt = conn.createStatement();

                rowCountDeleted = stmt.executeUpdate(deleteStatement);

                System.out.println("Rows Deleted: " + rowCountDeleted);


            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            finally

            {

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

            return rowCountDeleted;
        }





        public List<BookReview> getBookReviews(
                Integer bookID,
                Integer memberID,
                String sortBy,
                Integer limit, Integer offset
        ) {



            boolean isFirst = true;

            String query = "";

            String queryNormal = "SELECT * FROM " + BookReview.TABLE_NAME;


            String queryJoin = "SELECT "

                    + BookReview.TABLE_NAME + "." + BookReview.BOOK_REVIEW_ID + ","
                    + BookReview.TABLE_NAME + "." + BookReview.BOOK_ID + ","
                    + BookReview.TABLE_NAME + "." + BookReview.MEMBER_ID + ","
                    + BookReview.TABLE_NAME + "." + BookReview.RATING + ","
                    + BookReview.TABLE_NAME + "." + BookReview.REVIEW_TEXT + ","
                    + BookReview.TABLE_NAME + "." + BookReview.REVIEW_DATE + ","
                    + BookReview.TABLE_NAME + "." + BookReview.REVIEW_TITLE + ""


                    + " FROM " + BookReview.TABLE_NAME;


            if(bookID != null)
            {
                queryJoin = queryJoin + " WHERE "
                        + BookReview.TABLE_NAME
                        + "."
                        + BookReview.BOOK_ID + " = " + bookID;


                queryNormal = queryNormal + " WHERE "
                        + BookReview.TABLE_NAME
                        + "."
                        + BookReview.BOOK_ID + " = " + bookID;

                isFirst = false;
            }


            if(memberID != null)
            {

                String queryPartMember =
                        BookReview.TABLE_NAME
                                + "."
                        + BookReview.MEMBER_ID + " = " + memberID;

                if(isFirst)
                {
                    queryJoin = queryJoin + " WHERE " + queryPartMember;
                    queryNormal = queryNormal + " WHERE " + queryPartMember;

                }else
                {
                    queryJoin = queryJoin + " AND " + queryPartMember;
                    queryNormal = queryNormal + " AND " + queryPartMember;
                }


                isFirst = false;

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



            ArrayList<BookReview> bookReviewsList = new ArrayList<BookReview>();


            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {

                conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
                        JDBCContract.CURRENT_USERNAME,
                        JDBCContract.CURRENT_PASSWORD);

                stmt = conn.createStatement();

                rs = stmt.executeQuery(query);

                while(rs.next())
                {
                    BookReview bookReview = new BookReview();

                    bookReview.setBookReviewID(rs.getInt(BookReview.BOOK_REVIEW_ID));
                    bookReview.setBookID(rs.getInt(BookReview.BOOK_ID));
                    bookReview.setMemberID(rs.getInt(BookReview.MEMBER_ID));
                    bookReview.setRating(rs.getInt(BookReview.RATING));
                    bookReview.setReviewText(rs.getString(BookReview.REVIEW_TEXT));

                    bookReview.setReviewTitle(rs.getString(BookReview.REVIEW_TITLE));
                    bookReview.setReviewDate(rs.getTimestamp(BookReview.REVIEW_DATE));

                    bookReviewsList.add(bookReview);
                }



                System.out.println("books By CategoryID " + bookReviewsList.size());

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

            return bookReviewsList;
        }



        public BookReviewEndpoint getEndPointMetadata(
                Integer bookID,
                Integer memberID)
        {


            boolean isFirst = true;

            String query = "";

            String queryNormal = "SELECT "
                    + "count( DISTINCT " + BookReview.BOOK_REVIEW_ID + ") as item_count" + ""
                    + " FROM " + BookReview.TABLE_NAME;




            if(bookID != null)
            {

                queryNormal = queryNormal + " WHERE "
                        + BookReview.TABLE_NAME
                        + "."
                        + BookReview.BOOK_ID + " = " + bookID;

                isFirst = false;
            }


            if(memberID != null)
            {

                String queryPartMember =
                        BookReview.TABLE_NAME
                                + "."
                                + BookReview.MEMBER_ID + " = " + memberID;

                if(isFirst)
                {
                    queryNormal = queryNormal + " WHERE " + queryPartMember;

                }else
                {
                    queryNormal = queryNormal + " AND " + queryPartMember;
                }


                isFirst = false;

            }




/*
            if(bookID != null)
            {
*//*
                queryJoin = queryJoin + " AND "
                        + ItemContract.TABLE_NAME
                        + "."
                        + ItemContract.ITEM_CATEGORY_ID + " = " + itemCategoryID;

*//*


                //" WHERE ITEM_CATEGORY_ID = " + itemCategoryID

                queryNormal = queryNormal + " WHERE "
                        + BookReview.TABLE_NAME
                        + "."
                        + BookReview.BOOK_ID + " = " + bookID;
            }



            if(memberID != null)
            {
*//*
                queryJoin = queryJoin + " AND "
                        + ItemContract.TABLE_NAME
                        + "."
                        + ItemContract.ITEM_CATEGORY_ID + " = " + itemCategoryID;

*//*


                //" WHERE ITEM_CATEGORY_ID = " + itemCategoryID

                queryNormal = queryNormal + " WHERE "
                        + BookReview.TABLE_NAME
                        + "."
                        + BookReview.MEMBER_ID + " = " + memberID;
            }*/



            // Applying filters





		/*
		Applying filters Ends
		 */



            query = queryNormal;


            BookReviewEndpoint endPoint = new BookReviewEndpoint();


            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {

                conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
                        JDBCContract.CURRENT_USERNAME,
                        JDBCContract.CURRENT_PASSWORD);

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





        public BookReview getBookReview(int bookReviewID)
        {

            String query = "SELECT * FROM " +  BookReview.TABLE_NAME
                    + " WHERE " + BookReview.BOOK_REVIEW_ID + " = " + bookReviewID;


            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;


            //ItemCategory itemCategory = new ItemCategory();
            BookReview bookReview = null;

            try {

                conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
                        JDBCContract.CURRENT_USERNAME,
                        JDBCContract.CURRENT_PASSWORD);

                stmt = conn.createStatement();

                rs = stmt.executeQuery(query);



                while(rs.next())
                {
                    bookReview = new BookReview();

                    bookReview.setBookReviewID(rs.getInt(BookReview.BOOK_REVIEW_ID));
                    bookReview.setBookID(rs.getInt(BookReview.BOOK_ID));
                    bookReview.setMemberID(rs.getInt(BookReview.MEMBER_ID));
                    bookReview.setRating(rs.getInt(BookReview.RATING));
                    bookReview.setReviewText(rs.getString(BookReview.REVIEW_TEXT));

                    bookReview.setReviewTitle(rs.getString(BookReview.REVIEW_TITLE));
                    bookReview.setReviewDate(rs.getTimestamp(BookReview.REVIEW_DATE));

                    System.out.println("Get BookReview by ID : " + bookReview.getBookID());
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

            return bookReview;
        }


}
