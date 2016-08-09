package org.nearbyshops.communitylibrary;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.nearbyshops.communitylibrary.Model.*;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/api/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */


    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in org.nearbyshops.communitylibrary package
        final ResourceConfig rc = new ResourceConfig().packages("org.nearbyshops.communitylibrary");

        rc.register(RolesAllowedDynamicFeature.class);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));


        createTables();

        System.in.read();
        server.stop();
    }





    public static void createTables()
    {

        Connection conn = null;
        Statement stmt = null;

        try {

            conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL
                    ,JDBCContract.CURRENT_USERNAME
                    , JDBCContract.CURRENT_PASSWORD);

            stmt = conn.createStatement();


            // Create Tables If THEY DO Not Exist
            stmt.executeUpdate(Member.createTableMemberPostgres);
            stmt.executeUpdate(BookCategory.createTableBookCategoryPostgres);
            stmt.executeUpdate(Book.createTableBookPostgres);
            stmt.executeUpdate(FavouriteBook.createTableFavouriteBookPostgres);
            stmt.executeUpdate(BookReview.createTableBookReviewPostgres);


            System.out.println("Tables Created ... !");


            // developers Note: whenever adding a table please check that its dependencies are already created.




        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally{


            // close the connection and statement object

            if(stmt !=null)
            {

                try {
                    stmt.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }


            if(conn!=null)
            {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }


}

