package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;

import db.mongodb.MongoDBUtil;

/**
 * Servlet implementation class AddUser
 */
@WebServlet(name = "adduser", urlPatterns = { "/adduser" })
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		MongoClient mongoClient = new MongoClient();
	    MongoDatabase db = mongoClient.getDatabase(MongoDBUtil.DB_NAME);

	    // Step 1: remove old tables.
	    db.getCollection("users").drop();
	    db.getCollection("items").drop();

	    // Step 2: create new tables, populate data and create index.
//	    db.getCollection("users")
//	        .insertOne(new Document().append("first_name", "John").append("last_name", "Smith")
//	            .append("password", "3229c1097c00d497a0fd282d586be050").append("user_id", "1111"));
	    
	    db.getCollection("users")
        .insertOne(new Document().append("first_name", "Jiepeng").append("last_name", "Sun")
            .append("password", "af17a6d2be6676b4cf53b3ae81796fa6").append("user_id", "123"));
	    
	    
	    // make sure user_id is unique.
	    IndexOptions indexOptions = new IndexOptions().unique(true);

	    // use 1 for ascending index , -1 for descending index
	    // Different to MySQL, users table in MongoDB also has history info.
	    db.getCollection("users").createIndex(new Document("user_id", 1), indexOptions);

	    // make sure item_id is unique.
	    // Different to MySQL, items table in MongoDB also has categories info.
	    db.getCollection("items").createIndex(new Document("item_id", 1), indexOptions);

	    mongoClient.close();
	    System.out.println("Import is done successfully.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			
			MongoClient mongoClient = new MongoClient();
		    MongoDatabase db = mongoClient.getDatabase(MongoDBUtil.DB_NAME);
		    JSONObject msg = new JSONObject();
		    
		    // get request parameters for userID and password
		    String firstname = request.getParameter("firstname");
		    String lastname = request.getParameter("lastname");
		 	String user = request.getParameter("user_id");
		 	String pwd = request.getParameter("password");
			
			db.getCollection("users")
	        .insertOne(new Document().append("first_name", firstname).append("last_name", lastname)
	            .append("password", pwd).append("user_id", user));
						
			msg.put("status", "OK");
			msg.put("user_id", user);
			msg.put("name", firstname + " " + lastname);
			RpcHelper.writeJsonObject(response, msg);
			mongoClient.close();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}