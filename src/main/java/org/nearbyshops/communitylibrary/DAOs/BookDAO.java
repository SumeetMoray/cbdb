package org.nearbyshops.communitylibrary.DAOs;

import org.nearbyshops.communitylibrary.Model.Book;
import org.nearbyshops.communitylibrary.Model.BookCategory;
import org.nearbyshops.communitylibrary.JDBCContract;
import org.nearbyshops.communitylibrary.Model.BookReview;
import org.nearbyshops.communitylibrary.Model.FavouriteBook;
import org.nearbyshops.communitylibrary.ModelEndpoint.BookEndpoint;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sumeet on 8/8/16.
 */
public class BookDAO {


        @Override
        protected void finalize() throws Throwable {
            // TODO Auto-generated method stub
            super.finalize();
        }


        public int saveBook(Book book)
        {


            Connection conn = null;
            Statement stmt = null;
            int idOfInsertedRow = 0;


            String insertStatement = "INSERT INTO "
                    + Book.TABLE_NAME
                    + "("
                    + Book.AUTHOR_NAME + ","
                    + Book.BACKDROP_IMAGE_URL + ","
                    + Book.BOOK_CATEGORY_ID + ","
                    + Book.BOOK_COVER_IMAGE_URL + ","

                    + Book.BOOK_DESCRIPTION + ","
                    + Book.TIMESTAMP_UPDATED + ","
                    + Book.BOOK_NAME + ","

                    + Book.DATE_OF_PUBLISH + ","
                    + Book.PUBLISHER_NAME + ","
                    + Book.PAGES_TOTAL + ""

                    + ") VALUES("
                    + "'" + book.getAuthorName() + "',"
                    + "'" + book.getBackdropImageURL() + "',"
                    + book.getBookCategoryID() + ","
                    + "'" + book.getBookCoverImageURL() + "',"
                    + "'" + book.getBookDescription() + "',"
                    + "'" + "now()" + "',"
                    + "'" + book.getBookName() + "',";


            System.out.println();

            if(book.getDateOfPublish()==null)
            {
                insertStatement = insertStatement + " NULL ,";

                System.out.println("Date of Publish : null ");
            }
            else
            {
                System.out.println("Date of Publish : " + book.getDateOfPublish().toString());

                insertStatement = insertStatement + "'" + book.getDateOfPublish() + "',";
            }



            insertStatement = insertStatement
                                + "'" + book.getNameOfPublisher() + "',"
                                + "" + book.getPagesTotal() + ""
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


        public int updateBook(Book book)
        {

            //,int itemCategoryID

            //item.setItemCategoryID(itemCategoryID);

            String updateStatement = "UPDATE " + Book.TABLE_NAME

                    + " SET "

                    + Book.BOOK_CATEGORY_ID + " = " + "" + book.getBookCategoryID() + "" + ","
                    + Book.BOOK_NAME + " = " + "'" + book.getBookName() + "'" + ","
                    + Book.BOOK_COVER_IMAGE_URL + " = " + "'" + book.getBookCoverImageURL() + "'" + ","
                    + Book.BACKDROP_IMAGE_URL + " = " + "'" + book.getBackdropImageURL() + "'" + ","
                    + Book.AUTHOR_NAME + " = " + "'" + book.getAuthorName() + "'" + ","
                    + Book.BOOK_DESCRIPTION + " = " + "'" + book.getBookDescription() + "'" + ","
                    + Book.TIMESTAMP_UPDATED + " = " + "'" + "now()" + "'" + ",";


            if(book.getDateOfPublish()!=null)
            {
                updateStatement = updateStatement + Book.DATE_OF_PUBLISH + " = " + "'" + book.getDateOfPublish() + "'" + ",";
            }

            updateStatement = updateStatement
                    + Book.PUBLISHER_NAME + " = " + "'" + book.getNameOfPublisher() + "'" + ","
                    + Book.PAGES_TOTAL + " = " + "'" + book.getPagesTotal() + "'" + ""

                    + " WHERE "
                    + Book.BOOK_ID + " = " + book.getBookID();

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



        public int deleteBook(int bookID)
        {

            String deleteStatement = "DELETE FROM " + Book.TABLE_NAME
                    + " WHERE " + Book.BOOK_ID + " = " + bookID;

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





        public List<Book> getBooks(
                Integer bookCategoryID,
                Integer favouriteBookMemberID,
                String sortBy,
                Integer limit, Integer offset
        ) {

            boolean isFirst = true;

            String query = "";

            String queryNormal = "SELECT * FROM " + Book.TABLE_NAME;


            String queryJoin = "SELECT "

                    + Book.TABLE_NAME + "." + Book.BOOK_ID + ","
                    + Book.TABLE_NAME + "." + Book.BOOK_CATEGORY_ID + ","
                    + Book.TABLE_NAME + "." + Book.BOOK_NAME + ","
                    + Book.TABLE_NAME + "." + Book.BOOK_COVER_IMAGE_URL + ","
                    + Book.TABLE_NAME + "." + Book.BACKDROP_IMAGE_URL + ","
                    + Book.TABLE_NAME + "." + Book.AUTHOR_NAME + ","
                    + Book.TABLE_NAME + "." + Book.BOOK_DESCRIPTION + ","
                    + Book.TABLE_NAME + "." + Book.TIMESTAMP_CREATED + ","
                    + Book.TABLE_NAME + "." + Book.TIMESTAMP_UPDATED + ","

                    + Book.TABLE_NAME + "." + Book.DATE_OF_PUBLISH + ","
                    + Book.TABLE_NAME + "." + Book.PUBLISHER_NAME + ","
                    + Book.TABLE_NAME + "." + Book.PAGES_TOTAL + ","

                    +  "avg(" + BookReview.TABLE_NAME + "." + BookReview.RATING + ") as avg_rating" + ","
                    +  "count(" + BookReview.TABLE_NAME + "." + BookReview.BOOK_ID + ") as rating_count" + ""

                    + " FROM "
                    + BookReview.TABLE_NAME  + " RIGHT OUTER JOIN " + Book.TABLE_NAME

                    + " ON ("
//                    + Book.TABLE_NAME + "." + Book.BOOK_CATEGORY_ID + "=" + BookCategory.TABLE_NAME + "." + BookCategory.BOOK_CATEGORY_ID
//                    + " AND "
                    + BookReview.TABLE_NAME + "." + BookReview.BOOK_ID + " = " + Book.TABLE_NAME + "." + Book.BOOK_ID + ")";


            if(favouriteBookMemberID!=null)
            {
                queryJoin = queryJoin +
                        " INNER JOIN " + FavouriteBook.TABLE_NAME
                        + " ON (" + Book.TABLE_NAME + "." + Book.BOOK_ID + " = " + FavouriteBook.TABLE_NAME + "."  + FavouriteBook.BOOK_ID + ")"
                        + " WHERE (" + FavouriteBook.TABLE_NAME + "." + FavouriteBook.MEMBER_ID + " = " + favouriteBookMemberID + ")";

                isFirst = false;
            }


            if(bookCategoryID != null)
            {


                String queryPartBookCategory = Book.TABLE_NAME
                        + "."
                        + Book.BOOK_CATEGORY_ID + " = " + bookCategoryID;


                if(isFirst)
                {
                    queryJoin = queryJoin + " WHERE " + queryPartBookCategory;
                    queryNormal = queryNormal + " WHERE " + queryPartBookCategory;

                }else
                {
                    queryJoin = queryJoin + " AND " + queryPartBookCategory;
                    queryNormal = queryNormal + " AND " + queryPartBookCategory;
                }


                isFirst = false;
            }




            // all the non-aggregate columns which are present in select must be present in group by also.

            queryJoin = queryJoin

                    + " group by "
                    + Book.TABLE_NAME + "." + Book.BOOK_ID + ","
                    + Book.TABLE_NAME + "." + Book.BOOK_CATEGORY_ID + ","
                    + Book.TABLE_NAME + "." + Book.BOOK_NAME + ","
                    + Book.TABLE_NAME + "." + Book.BOOK_COVER_IMAGE_URL + ","
                    + Book.TABLE_NAME + "." + Book.BACKDROP_IMAGE_URL + ","
                    + Book.TABLE_NAME + "." + Book.AUTHOR_NAME + ","
                    + Book.TABLE_NAME + "." + Book.BOOK_DESCRIPTION + ","
                    + Book.TABLE_NAME + "." + Book.TIMESTAMP_CREATED + ","
                    + Book.TABLE_NAME + "." + Book.TIMESTAMP_UPDATED + ","
                    + Book.TABLE_NAME + "." + Book.DATE_OF_PUBLISH + ","
                    + Book.TABLE_NAME + "." + Book.PUBLISHER_NAME + ","
                    + Book.TABLE_NAME + "." + Book.PAGES_TOTAL + "";



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



            ArrayList<Book> bookList = new ArrayList<Book>();


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
                    Book book = new Book();

                    book.setAuthorName(rs.getString(Book.AUTHOR_NAME));
                    book.setBackdropImageURL(rs.getString(Book.BACKDROP_IMAGE_URL));
                    book.setBookCategoryID(rs.getInt(Book.BOOK_CATEGORY_ID));
                    book.setBookCoverImageURL(rs.getString(Book.BOOK_COVER_IMAGE_URL));
                    book.setBookDescription(rs.getString(Book.BOOK_DESCRIPTION));
                    book.setBookID(rs.getInt(Book.BOOK_ID));
                    book.setBookName(rs.getString(Book.BOOK_NAME));
                    book.setTimestampCreated(rs.getTimestamp(Book.TIMESTAMP_CREATED));
                    book.setTimeStampUpdated(rs.getTimestamp(Book.TIMESTAMP_UPDATED));

                    book.setDateOfPublish(rs.getTimestamp(Book.DATE_OF_PUBLISH));
                    book.setNameOfPublisher(rs.getString(Book.PUBLISHER_NAME));
                    book.setPagesTotal(rs.getInt(Book.PAGES_TOTAL));

                    book.setRt_rating_avg(rs.getFloat("avg_rating"));
                    book.setRt_rating_count(rs.getFloat("rating_count"));


                    bookList.add(book);
                }



                System.out.println("books By CategoryID " + bookList.size());

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



        public BookEndpoint getEndPointMetadata(
                Integer bookCategoryID,
                Integer favouriteBookMemberID)
        {

            boolean isFirst = true;

            String query = "";

            String queryNormal = "SELECT "
                    + "count(*) as item_count" + ""
                    + " FROM " + Book.TABLE_NAME;


            if(favouriteBookMemberID!=null)
            {
                queryNormal = queryNormal +
                        " INNER JOIN " + FavouriteBook.TABLE_NAME
                        + " ON (" + Book.TABLE_NAME + "." + Book.BOOK_ID + " = " + FavouriteBook.TABLE_NAME + "."  + FavouriteBook.BOOK_ID + ")"
                        + " WHERE (" + FavouriteBook.TABLE_NAME + "." + FavouriteBook.MEMBER_ID + " = " + favouriteBookMemberID + ")";

                isFirst = false;
            }


            if(bookCategoryID != null)
            {


                String queryPartBookCategory = Book.TABLE_NAME
                        + "."
                        + Book.BOOK_CATEGORY_ID + " = " + bookCategoryID;


                if(isFirst)
                {
//                    queryJoin = queryJoin + " WHERE " + queryPartBookCategory;
                    queryNormal = queryNormal + " WHERE " + queryPartBookCategory;

                }else
                {
//                    queryJoin = queryJoin + " AND " + queryPartBookCategory;
                    queryNormal = queryNormal + " AND " + queryPartBookCategory;
                }


                isFirst = false;
            }



/*

            if(bookCategoryID != null)
            {
*//*
                queryJoin = queryJoin + " AND "
                        + ItemContract.TABLE_NAME
                        + "."
                        + ItemContract.ITEM_CATEGORY_ID + " = " + itemCategoryID;

*//*


                //" WHERE ITEM_CATEGORY_ID = " + itemCategoryID

                queryNormal = queryNormal + " WHERE "
                        + Book.TABLE_NAME
                        + "."
                        + Book.BOOK_CATEGORY_ID + " = " + bookCategoryID;

            }*/



            // Applying filters





		/*
		Applying filters Ends
		 */



            query = queryNormal;


            BookEndpoint endPoint = new BookEndpoint();


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



        public Book getBook(int bookID)
        {

            String query = "SELECT * FROM " +  Book.TABLE_NAME
                    + " WHERE " + Book.BOOK_ID + " = " + bookID;


            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;


            //ItemCategory itemCategory = new ItemCategory();
            Book book = null;

            try {

                conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
                        JDBCContract.CURRENT_USERNAME,
                        JDBCContract.CURRENT_PASSWORD);

                stmt = conn.createStatement();

                rs = stmt.executeQuery(query);



                while(rs.next())
                {
                    book = new Book();

                    book.setAuthorName(rs.getString(Book.AUTHOR_NAME));
                    book.setBackdropImageURL(rs.getString(Book.BACKDROP_IMAGE_URL));
                    book.setBookCategoryID(rs.getInt(Book.BOOK_CATEGORY_ID));
                    book.setBookCoverImageURL(rs.getString(Book.BOOK_COVER_IMAGE_URL));
                    book.setBookDescription(rs.getString(Book.BOOK_DESCRIPTION));
                    book.setBookID(rs.getInt(Book.BOOK_ID));
                    book.setBookName(rs.getString(Book.BOOK_NAME));
                    book.setTimestampCreated(rs.getTimestamp(Book.TIMESTAMP_CREATED));
                    book.setTimeStampUpdated(rs.getTimestamp(Book.TIMESTAMP_UPDATED));

                    book.setDateOfPublish(rs.getTimestamp(Book.DATE_OF_PUBLISH));
                    book.setNameOfPublisher(rs.getString(Book.PUBLISHER_NAME));
                    book.setPagesTotal(rs.getInt(Book.PAGES_TOTAL));

                    System.out.println("Get Book by ID : " + book.getBookID());
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

            return book;
        }


}
