package org.nearbyshops.communitylibrary;

/**
 * Created by sumeet on 6/8/16.
 */
public class JDBCContract {

    private static final String DERBY_EMBEDDED_CONNECTION_URL = "jdbc:derby:sampleDB;create=true";
    private static String DERBY_USERNAME = "ME";
    private static String DERBY_PASSWORD = "MINE";

    private static final String POSTGRES_CONNECTION_URL = "jdbc:postgresql://localhost:5432/postgres";

    //"jdbc:postgresql://aa1vpy11adzdcrh.cxsl0shv40xg.ap-southeast-1.rds.amazonaws.com:5432/postgres"
    //"jdbc:postgresql://localhost:5432/postgres"
    //"jdbc:postgresql://localhost:5433/postgres"
    //"jdbc:postgresql://aa1vpy11adzdcrh.cxsl0shv40xg.ap-southeast-1.rds.amazonaws.com:5432/postgres"
    private static String POSTGRES_USERNAME = "postgres";
    private static String POSTGRES_PASSWORD = "password";


    public static String CURRENT_CONNECTION_URL = POSTGRES_CONNECTION_URL;

    public static String CURRENT_USERNAME = POSTGRES_USERNAME;
    public static String CURRENT_PASSWORD = POSTGRES_PASSWORD;

}
