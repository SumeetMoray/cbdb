package org.nearbyshops.communitylibrary.DAOsPrepared;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.communitylibrary.Globals.Globals;
import org.nearbyshops.communitylibrary.JDBCContract;
import org.nearbyshops.communitylibrary.Model.Book;
import org.nearbyshops.communitylibrary.Model.BookCategory;
import org.nearbyshops.communitylibrary.ModelEndpoint.BookCategoryEndpoint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sumeet on 8/8/16.
 */
public class BookCategoryDAOPrepared {

        HikariDataSource dataSource = Globals.getDataSource();


        @Override
        protected void finalize() throws Throwable {
            // TODO Auto-generated method stub
            super.finalize();
        }




        public int saveBookCategory(BookCategory bookCategory)
        {


            Connection conn = null;
            PreparedStatement stmt = null;
            int idOfInsertedRow = 0;

            String insertStatement = "INSERT INTO "
                    + BookCategory.TABLE_NAME
                    + "("
                    + BookCategory.BOOK_CATEGORY_NAME + ","
                    + BookCategory.IMAGE_URL + ","
                    + BookCategory.BACKDROP_IMAGE_URL + ","
                    + BookCategory.PARENT_CATEGORY_ID + ""
                    + ") VALUES(?,?,?,?)";

            try {

                conn = dataSource.getConnection();

                stmt = conn.prepareStatement(insertStatement,PreparedStatement.RETURN_GENERATED_KEYS);

                stmt.setString(1,bookCategory.getBookCategoryName());
                stmt.setString(2,bookCategory.getImageURL());
                stmt.setString(3,bookCategory.getBackdropImageURL());
                stmt.setInt(4,bookCategory.getParentCategoryID());

                idOfInsertedRow = stmt.executeUpdate();

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


        public int updateBookCategory(BookCategory bookCategory)
        {

            //,int itemCategoryID

            //item.setItemCategoryID(itemCategoryID);

            String updateStatement = "UPDATE "

                    + BookCategory.TABLE_NAME

                    + " SET "
                    + BookCategory.BOOK_CATEGORY_NAME + " = ?,"
                    + BookCategory.IMAGE_URL + " = ? ,"
                    + BookCategory.BACKDROP_IMAGE_URL + " = ? ,"
                    + BookCategory.PARENT_CATEGORY_ID + " = ? "

                    + " WHERE "
                    + BookCategory.BOOK_CATEGORY_ID + " = ? ";

            Connection conn = null;
            PreparedStatement stmt = null;

            int rowCountUpdated = 0;

            try {

                conn = dataSource.getConnection();
                stmt = conn.prepareStatement(updateStatement);

                stmt.setString(1,bookCategory.getBookCategoryName());
                stmt.setString(2,bookCategory.getImageURL());
                stmt.setString(3,bookCategory.getBackdropImageURL());
                stmt.setInt(4,bookCategory.getParentCategoryID());
                stmt.setInt(5,bookCategory.getBookCategoryID());

                rowCountUpdated = stmt.executeUpdate();


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



        public int deleteBookCategory(int bookCategoryID)
        {

            String deleteStatement = "DELETE FROM " + BookCategory.TABLE_NAME
                    + " WHERE " + BookCategory.BOOK_CATEGORY_ID + " = ? ";

            Connection conn= null;
            PreparedStatement stmt = null;
            int rowCountDeleted = 0;
            try {


                conn = dataSource.getConnection();
                stmt = conn.prepareStatement(deleteStatement);
                stmt.setInt(1,bookCategoryID);


                rowCountDeleted = stmt.executeUpdate();

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





        public List<BookCategory> getBookCategories(
                Integer parentCategoryID,
                String sortBy,
                Integer limit, Integer offset
        ) {
            String query = "";

            String queryNormal = "SELECT * FROM " + BookCategory.TABLE_NAME;


            String queryJoin = "SELECT "

                    + BookCategory.TABLE_NAME + "." + BookCategory.BOOK_CATEGORY_ID + ","
                    + BookCategory.TABLE_NAME + "." + BookCategory.BOOK_CATEGORY_NAME + ","
                    + BookCategory.TABLE_NAME + "." + BookCategory.IMAGE_URL + ","
                    + BookCategory.TABLE_NAME + "." + BookCategory.BACKDROP_IMAGE_URL + ","
                    + BookCategory.TABLE_NAME + "." + BookCategory.PARENT_CATEGORY_ID + ","

                    +  "count(" + Book.TABLE_NAME + "." + Book.BOOK_ID + ") as book_count" + ""

                    + " FROM "
                    + BookCategory.TABLE_NAME  + " LEFT OUTER JOIN " + Book.TABLE_NAME

                    + " ON ("
//                    + Book.TABLE_NAME + "." + Book.BOOK_CATEGORY_ID + "=" + BookCategory.TABLE_NAME + "." + BookCategory.BOOK_CATEGORY_ID
//                    + " AND "
                    + BookCategory.TABLE_NAME + "." + BookCategory.BOOK_CATEGORY_ID + " = " + Book.TABLE_NAME + "." + Book.BOOK_CATEGORY_ID + ")";



            if(parentCategoryID != null)
            {

                queryJoin = queryJoin + " WHERE "
                        + BookCategory.TABLE_NAME
                        + "."
                        + BookCategory.PARENT_CATEGORY_ID + " = " + parentCategoryID;

                queryNormal = queryNormal + " WHERE "
                        + BookCategory.TABLE_NAME
                        + "."
                        + BookCategory.PARENT_CATEGORY_ID + " = " + parentCategoryID;

            }



            // all the non-aggregate columns which are present in select must be present in group by also.

            queryJoin = queryJoin + " group by "
                    + BookCategory.TABLE_NAME + "." + BookCategory.BOOK_CATEGORY_ID + ","
                    + BookCategory.TABLE_NAME + "." + BookCategory.BOOK_CATEGORY_NAME + ","
                    + BookCategory.TABLE_NAME + "." + BookCategory.IMAGE_URL + ","
                    + BookCategory.TABLE_NAME + "." + BookCategory.BACKDROP_IMAGE_URL + ","
                    + BookCategory.TABLE_NAME + "." + BookCategory.PARENT_CATEGORY_ID + "";



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


            ArrayList<BookCategory> bookCategoryList = new ArrayList<>();


            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {

                conn = dataSource.getConnection();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

                while(rs.next())
                {

                    BookCategory bookCategory = new BookCategory();

                    bookCategory.setBookCategoryID(rs.getInt(BookCategory.BOOK_CATEGORY_ID));
                    bookCategory.setBackdropImageURL(rs.getString(BookCategory.BACKDROP_IMAGE_URL));
                    bookCategory.setBookCategoryName(rs.getString(BookCategory.BOOK_CATEGORY_NAME));
                    bookCategory.setImageURL(rs.getString(BookCategory.IMAGE_URL));
                    bookCategory.setParentCategoryID(rs.getInt(BookCategory.PARENT_CATEGORY_ID));

                    bookCategory.setRt_book_count(rs.getInt("book_count"));

                    bookCategoryList.add(bookCategory);
                }



                System.out.println("bookCategories " + bookCategoryList.size());

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

            return bookCategoryList;
        }



        public BookCategoryEndpoint getEndPointMetadata(
                Integer parentCategoryID)
        {


            String query = "";

            String queryNormal = "SELECT "
                    + "count( DISTINCT " + BookCategory.BOOK_CATEGORY_ID + ") as item_count" + ""
                    + " FROM " + BookCategory.TABLE_NAME;


            if(parentCategoryID != null)
            {
/*
                queryJoin = queryJoin + " AND "
                        + ItemContract.TABLE_NAME
                        + "."
                        + ItemContract.ITEM_CATEGORY_ID + " = " + itemCategoryID;

*/


                //" WHERE ITEM_CATEGORY_ID = " + itemCategoryID

                queryNormal = queryNormal + " WHERE "
                        + BookCategory.TABLE_NAME
                        + "."
                        + BookCategory.PARENT_CATEGORY_ID + " = " + parentCategoryID;

            }



            // Applying filters





		/*
		Applying filters Ends
		 */



            query = queryNormal;


            BookCategoryEndpoint endPoint = new BookCategoryEndpoint();


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



        public BookCategory getBookCategory(int bookCategoryID)
        {

            String query = "SELECT * FROM " +  BookCategory.TABLE_NAME
                    + " WHERE " + BookCategory.BOOK_CATEGORY_ID + " = " + bookCategoryID;


            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            BookCategory bookCategory = null;

            try {

                conn = dataSource.getConnection();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);


                while(rs.next())
                {
                    bookCategory = new BookCategory();

                    bookCategory.setParentCategoryID(rs.getInt(BookCategory.PARENT_CATEGORY_ID));
                    bookCategory.setImageURL(rs.getString(BookCategory.IMAGE_URL));
                    bookCategory.setBookCategoryName(rs.getString(BookCategory.BOOK_CATEGORY_NAME));
                    bookCategory.setBackdropImageURL(rs.getString(BookCategory.BACKDROP_IMAGE_URL));
                    bookCategory.setBookCategoryID(rs.getInt(BookCategory.BOOK_CATEGORY_ID));

                    System.out.println("Get BookCategory by ID : " + bookCategory.getBookCategoryID());
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

            return bookCategory;
        }


}
