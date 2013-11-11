package com.gymlife.workoutservice.db.util;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
 
public class ConnectionFactory {
    //static reference to itself
    private static ConnectionFactory instance = new ConnectionFactory();
    private static BoneCP connectionPool;
    
    private static final String URL = "jdbc:mysql://stevie.heliohost.org:3306/jpachter_workout";
	//Local = jdbc:mysql://127.0.0.1:3306/workout_db?user=root&password=mypass
    private static final String USER = "jpachter";
    private static final String PASSWORD = "juke23gl";
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    
    //private constructor
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER_CLASS);
            BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(URL); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
			config.setUsername(USER);
			config.setPassword(PASSWORD);
			config.setMinConnectionsPerPartition(1);
			config.setMaxConnectionsPerPartition(2);
			config.setPartitionCount(1);
			connectionPool = new BoneCP(config);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("ERROR: Unable to Connect to Database.");
            e.printStackTrace();
		}
    }
 
    private Connection createConnection() {
        Connection connection = null;
        try {
			connection = connectionPool.getConnection(); // fetch a connection
        } catch (SQLException e) {
            System.out.println("ERROR: Failure fetching connection.");
        }
        return connection;
    }  
 
    public static Connection getConnection() {
        return instance.createConnection();
    }
}
