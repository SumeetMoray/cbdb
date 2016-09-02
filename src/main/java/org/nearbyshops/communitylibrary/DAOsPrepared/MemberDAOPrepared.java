package org.nearbyshops.communitylibrary.DAOsPrepared;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.communitylibrary.Globals.Globals;
import org.nearbyshops.communitylibrary.JDBCContract;
import org.nearbyshops.communitylibrary.Model.Member;
import org.nearbyshops.communitylibrary.ModelEndpoint.MemberEndpoint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sumeet on 8/8/16.
 */
public class MemberDAOPrepared {


        private HikariDataSource dataSource = Globals.getDataSource();



        @Override
        protected void finalize() throws Throwable {
            // TODO Auto-generated method stub
            super.finalize();
        }

        public int saveMember(Member member)
        {


            Connection conn = null;
            PreparedStatement statement = null;
            int idOfInsertedRow = 0;

            String insertStatement = "";


            insertStatement = "INSERT INTO "
                    + Member.TABLE_NAME
                    + "("
                    + Member.USER_NAME + ","
                    + Member.PASSWORD + ","
                    + Member.MEMBER_NAME + ","

                    + Member.PROFILE_IMAGE_URL + ","
                    + Member.CITY + ","
                    + Member.ABOUT + ""

                    + ") VALUES(?,?,?, ?,?,?)";


            try {

                conn = dataSource.getConnection();
                statement = conn.prepareStatement(insertStatement,PreparedStatement.RETURN_GENERATED_KEYS);

                statement.setString(1,member.getUserName());
                statement.setString(2,member.getPassword());
                statement.setString(3,member.getMemberName());

                statement.setString(4,member.getProfileImageURL());
                statement.setString(5,member.getCity());
                statement.setString(6,member.getAbout());


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


        public int updateMember(Member member)
        {

            //,int itemCategoryID

            //item.setItemCategoryID(itemCategoryID);

            String updateStatement = "UPDATE "
                    + Member.TABLE_NAME
                    + " SET "
                    + Member.USER_NAME + " = ?,"
                    + Member.PASSWORD + " = ?,"
                    + Member.MEMBER_NAME + " = ?,"

                    + Member.PROFILE_IMAGE_URL + " = ?,"
                    + Member.CITY + " = ?,"
                    + Member.ABOUT + " = ?,"

                    + Member.DATE_OF_BIRTH + " = ?"
                    + " WHERE "
                    + Member.MEMBER_ID + " = ?";


            Connection conn = null;
            PreparedStatement statement = null;

            int rowCountUpdated = 0;

            try {

                conn = dataSource.getConnection();
                statement = conn.prepareStatement(updateStatement);

                statement.setString(1,member.getMemberName());
                statement.setString(2,member.getPassword());
                statement.setString(3,member.getMemberName());

                statement.setString(4,member.getProfileImageURL());
                statement.setString(5,member.getCity());
                statement.setString(6,member.getAbout());

                statement.setTimestamp(7,member.getDateOfBirth());
                statement.setInt(8,member.getMemberID());


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



        public int deleteMember(int memberID)
        {

            String deleteStatement = "DELETE FROM " + Member.TABLE_NAME
                    + " WHERE " + Member.MEMBER_ID + " = ?";

            Connection connection= null;
            PreparedStatement statement = null;
            int rowCountDeleted = 0;
            try {

                connection = dataSource.getConnection();
                statement = connection.prepareStatement(deleteStatement);

                statement.setInt(1,memberID);

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

                    if(connection!=null)
                    {connection.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return rowCountDeleted;
        }





        public List<Member> getMembers(
                String sortBy,
                Integer limit, Integer offset
        ) {
            String query = "";

            String queryNormal = "SELECT * FROM " + Member.TABLE_NAME;


/*
            if(parentCategoryID != null)
            {

                */
/*queryJoin = queryJoin + " AND "
                        + BookCategory.TABLE_NAME
                        + "."
                        + BookCategory.BOOK_CATEGORY_ID + " = " + bookCategoryID;
*//*



                //" WHERE ITEM_CATEGORY_ID = " + itemCategoryID

                queryNormal = queryNormal + " WHERE "
                        + BookCategory.TABLE_NAME
                        + "."
                        + BookCategory.PARENT_CATEGORY_ID + " = " + parentCategoryID;

            }
*/



            // Applying filters



            if(sortBy!=null)
            {
                if(!sortBy.equals(""))
                {
                    String queryPartSortBy = " ORDER BY " + sortBy;

                    queryNormal = queryNormal + queryPartSortBy;
//                    queryJoin = queryJoin + queryPartSortBy;
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
//                queryJoin = queryJoin + queryPartLimitOffset;
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


            ArrayList<Member> membersList = new ArrayList<>();


            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;

            try {

                connection = dataSource.getConnection();
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);

                while(resultSet.next())
                {

                    Member member = new Member();

                    member.setMemberID(resultSet.getInt(Member.MEMBER_ID));
                    member.setUserName(resultSet.getString(Member.USER_NAME));
//                    member.setPassword(rs.getString(Member.PASSWORD));
                    member.setMemberName(resultSet.getString(Member.MEMBER_NAME));
                    member.setProfileImageURL(resultSet.getString(Member.PROFILE_IMAGE_URL));
                    member.setCity(resultSet.getString(Member.CITY));
                    member.setAbout(resultSet.getString(Member.ABOUT));
//                    member.setDateOfBirth(rs.getTimestamp(Member.DATE_OF_BIRTH));

                    membersList.add(member);

                }



                System.out.println("Members List :  " + membersList.size());

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            finally

            {

                try {
                    if(resultSet!=null)
                    {resultSet.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {

                    if(statement!=null)
                    {statement.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {

                    if(connection!=null)
                    {connection.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return membersList;
        }



        public MemberEndpoint getEndPointMetadata()
        {


            String query = "";

            String queryNormal = "SELECT "
                    + "count( DISTINCT " + Member.MEMBER_ID + ") as item_count" + ""
                    + " FROM " + Member.TABLE_NAME;


            /*if(parentCategoryID != null)
            {
*//*
                queryJoin = queryJoin + " AND "
                        + ItemContract.TABLE_NAME
                        + "."
                        + ItemContract.ITEM_CATEGORY_ID + " = " + itemCategoryID;

*//*


                //" WHERE ITEM_CATEGORY_ID = " + itemCategoryID

                queryNormal = queryNormal + " WHERE "
                        + BookCategory.TABLE_NAME
                        + "."
                        + BookCategory.PARENT_CATEGORY_ID + " = " + parentCategoryID;

            }
*/


            // Applying filters





		/*
		Applying filters Ends
		 */



            query = queryNormal;


            MemberEndpoint endPoint = new MemberEndpoint();


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



        public Member getMember(int memberID)
        {

            String query = "SELECT * FROM " +  Member.TABLE_NAME
                    + " WHERE " + Member.MEMBER_ID + " = " + memberID;


            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            Member member = null;

            try {

                conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
                        JDBCContract.CURRENT_USERNAME,
                        JDBCContract.CURRENT_PASSWORD);

                stmt = conn.createStatement();

                rs = stmt.executeQuery(query);



                while(rs.next())
                {
                    member = new Member();

                    member.setMemberID(rs.getInt(Member.MEMBER_ID));
                    member.setUserName(rs.getString(Member.USER_NAME));
//                    member.setPassword(rs.getString(Member.PASSWORD));
                    member.setMemberName(rs.getString(Member.MEMBER_NAME));
                    member.setProfileImageURL(rs.getString(Member.PROFILE_IMAGE_URL));
                    member.setCity(rs.getString(Member.CITY));
                    member.setAbout(rs.getString(Member.ABOUT));
//                    member.setDateOfBirth(rs.getTimestamp(Member.DATE_OF_BIRTH));

                    System.out.println("Get Member by ID : " + member.getMemberID());
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

            return member;
        }





    public Member getMemberPassword(Integer memberID, String username)
    {


        String query = "";


        if(memberID!=null)
        {
            query = "SELECT * FROM " + Member.TABLE_NAME
                    + " WHERE ID = " + memberID;

        }

        else if(username!=null)
        {
            query = "SELECT * FROM " + Member.TABLE_NAME
                    + " WHERE " +  Member.USER_NAME + " = " + "'" + username + "'";

        }



        if(query.equals(""))
        {
            return null;
        }



        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;


//		Distributor distributor = null;
        Member endUser = null;

        try {


            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while(resultSet.next())
            {
                endUser = new Member();

                endUser.setMemberID(resultSet.getInt(Member.MEMBER_ID));
                endUser.setMemberName(resultSet.getString(Member.MEMBER_NAME));
                endUser.setUserName(resultSet.getString(Member.USER_NAME));
                endUser.setPassword(resultSet.getString(Member.PASSWORD));

                endUser.setProfileImageURL(resultSet.getString(Member.PROFILE_IMAGE_URL));
                endUser.setCity(resultSet.getString(Member.CITY));
                endUser.setAbout(resultSet.getString(Member.ABOUT));


            }


            //System.out.println("Total itemCategories queried " + itemCategoryList.size());



        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally

        {

            try {
                if(resultSet!=null)
                {resultSet.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return endUser;
    }


}
