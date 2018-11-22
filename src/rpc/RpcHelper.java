package rpc;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Item;

public class RpcHelper {
	  // Parses a JSONObject from http request.
	  public static JSONObject readJsonObject(HttpServletRequest request) {
	    StringBuffer jb = new StringBuffer();
	    String line = null;
	    try {
	      BufferedReader reader = request.getReader();//Body
	      while ((line = reader.readLine()) != null) {
	        jb.append(line);
	      }
	      reader.close();
	      return new JSONObject(jb.toString());
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return null;
	  }

	  // note: return JSON format to front-end
	  
	  // Writes a JSONObject to http response.
	  public static void writeJsonObject(HttpServletResponse response, JSONObject obj) {
	    try {
	      response.setContentType("application/json"); // content type, JSON
	      response.addHeader("Access-Control-Allow-Origin", "*");
	      PrintWriter out = response.getWriter();
	      out.print(obj);
	      // send response back to client
	      out.flush();
	      out.close();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

	  // Writes a JSONArray to http response.
	  public static void writeJsonArray(HttpServletResponse response, JSONArray array) {
	    try {
	      response.setContentType("application/json"); // content type, JSON
	      response.addHeader("Access-Control-Allow-Origin", "*");
	      PrintWriter out = response.getWriter();
	      out.print(array);
	      // send response back to client
	      out.flush();
	      out.close();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	  
	  // Converts a list of Item objects to JSONArray.
	  public static JSONArray getJSONArray(List<Item> items) {
	    JSONArray result = new JSONArray();
	    try {
	      for (Item item : items) {
	        result.put(item.toJSONObject());
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return result;
	  }
}

