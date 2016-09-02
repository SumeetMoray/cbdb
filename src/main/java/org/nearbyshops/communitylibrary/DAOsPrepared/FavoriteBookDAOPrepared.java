package org.nearbyshops.communitylibrary.DAOsPrepared;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.communitylibrary.Globals.Globals;
import org.nearbyshops.communitylibrary.JDBCContract;
import org.nearbyshops.communitylibrary.Model.FavouriteBook;
import org.nearbyshops.communitylibrary.ModelEndpoint.FavouriteBookEndpoint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sumeet on 8/8/16.
 */
public class FavoriteBookDAOPrepared {

    private HikariDataSource dataSource = Globals.getDataSource();

        @Override
        protected void finalize() throws Throwable {
            // TODO Auto-generated method stub
            super.finalize();
        }


        public int saveBook(FavouriteBook favouriteBook)
        {


            Connection conn = null;
            PreparedStatement statement = null;
            int idOfInsertedRow = 0;


            String insertStatement = "INSERT INTO "
                    + FavouriteBook.TABLE_NAME
                    + "("
                    + FavouriteBook.BOOK_ID + ","
                    + FavouriteBook.MEMBER_ID
                    + ") VALUES(?,?)";

            try {

                conn = dataSource.getConnection();
                statement = conn.prepareStatement(insertStatement,PreparedStatement.RETURN_GENERATED_KEYS);

                statement.setInt(1,favouriteBook.getBookID());
                statement.setInt(2,favouriteBook.getMemberID());

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



        public int deleteFavouriteBook(int bookID, int memberID)
        {

            String deleteStatement = "DELETE FROM " + FavouriteBook.TABLE_NAME
                    + " WHERE " + FavouriteBook.BOOK_ID + " = ?"
                    + " AND " + FavouriteBook.MEMBER_ID + " = ?";

            Connection conn= null;
            PreparedStatement statement = null;
            int rowCountDeleted = 0;
            try {


                conn = dataSource.getConnection();
                statement = conn.prepareStatement(deleteStatement);

                statement.setInt(1,bookID);
                statement.setInt(2,memberID);

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





        public List<FavouriteBook> getFavouriteBook(
                Integer bookID,
                Integer memberID,
                String sortBy,
                Integer limit, Integer offset
        ) {


            boolean isFirst = true;


            String query = "";

            String queryNormal = "SELECT * FROM " + FavouriteBook.TABLE_NAME;


            String queryJoin = "SELECT "

                    + FavouriteBook.TABLE_NAME + "." + FavouriteBook.BOOK_ID + ","
                    + FavouriteBook.TABLE_NAME + "." + FavouriteBook.MEMBER_ID + ""

                    + " FROM "
                    + FavouriteBook.TABLE_NAME;


            if(bookID != null)
            {
                queryJoin = queryJoin + " WHERE "
                        + FavouriteBook.TABLE_NAME
                        + "."
                        + FavouriteBook.BOOK_ID + " = " + bookID;



                //" WHERE ITEM_CATEGORY_ID = " + itemCategoryID

                queryNormal = queryNormal + " WHERE "
                        + FavouriteBook.TABLE_NAME
                        + "."
                        + FavouriteBook.BOOK_ID + " = " + bookID;

                isFirst = false;
            }



            if(memberID!=null){

                String queryPartMemberID;

                queryPartMemberID = FavouriteBook.TABLE_NAME
                                    + "."
                                    + FavouriteBook.MEMBER_ID + " = " + memberID;

                if(isFirst)
                {
                    queryJoin = queryJoin + " WHERE " + queryPartMemberID;
                    queryNormal = queryNormal + " WHERE " + queryPartMemberID;

                }else
                {
                    queryJoin = queryJoin + " AND " + queryPartMemberID;
                    queryNormal = queryNormal + " AND " + queryPartMemberID;
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



            query = queryNormal;

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



            ArrayList<FavouriteBook> bookList = new ArrayList<FavouriteBook>();


            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {


                conn = dataSource.getConnection();
                stmt = conn.createStatement();


//                System.out.println("Favourite Books " + query);

                rs = stmt.executeQuery(query);

                while(rs.next())
                {
                    FavouriteBook book = new FavouriteBook();

                    book.setMemberID(rs.getInt(FavouriteBook.MEMBER_ID));
                    book.setBookID(rs.getInt(FavouriteBook.BOOK_ID));

                    bookList.add(book);
                }



                System.out.println("Favourite Books " + bookList.size());

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



        public FavouriteBookEndpoint getEndPointMetadata(
                Integer bookID,
                Integer memberID)
        {


            boolean isFirst = true;


            String query = "";

            String queryNormal = "SELECT "
                    + "count(*) as item_count" + ""
                    + " FROM " + FavouriteBook.TABLE_NAME;




            if(bookID != null)
            {
            /*    queryJoin = queryJoin + " WHERE "
                        + FavouriteBook.TABLE_NAME
                        + "."
                        + FavouriteBook.BOOK_ID + " = " + bookID;



            */

                queryNormal = queryNormal + " WHERE "
                        + FavouriteBook.TABLE_NAME
                        + "."
                        + FavouriteBook.BOOK_ID + " = " + bookID;

                isFirst = false;
            }



            if(memberID!=null){

                String queryPartMemberID;

                queryPartMemberID = FavouriteBook.TABLE_NAME
                        + "."
                        + FavouriteBook.MEMBER_ID + " = " + memberID;

                if(isFirst)
                {
//                    queryJoin = queryJoin + " WHERE " + queryPartMemberID;
                    queryNormal = queryNormal + " WHERE " + queryPartMemberID;

                }else
                {
//                    queryJoin = queryJoin + " AND " + queryPartMemberID;
                    queryNormal = queryNormal + " AND " + queryPartMemberID;
                }

            }




            // Applying filters





		/*
		Applying filters Ends
		 */



            query = queryNormal;


            FavouriteBookEndpoint endPoint = new FavouriteBookEndpoint();


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

}
