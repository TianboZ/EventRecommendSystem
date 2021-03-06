package db;

import db.mongodb.MongoDBConnection;
import db.mysql.MySQLConnection;

// factory pattern
public class DBConnectionFactory {
	// This should change based on the pipeline.
	//private static final String DEFAULT_DB = "mysql";
	private static final String DEFAULT_DB = "mongodb";  // 在这里改没用！因为AddUser里面都是用的MongoDB的东西

	// Create a DBConnection based on given db type.
	public static DBConnection getDBConnection(String db) {
		switch (db) {
		case "mysql":
			return new MySQLConnection();
		case "mongodb":
			return MongoDBConnection.getInstance();

		// You may try other dbs and add them here.
		default:
			throw new IllegalArgumentException("Invalid db " + db);
		}
	}

	// This is overloading not overriding.
	public static DBConnection getDBConnection() {
		return getDBConnection(DEFAULT_DB);
	}
}


