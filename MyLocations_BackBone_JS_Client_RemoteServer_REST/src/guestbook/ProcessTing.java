package guestbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;



//import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
//import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
//import com.google.appengine.api.memcache.MemcacheService.SetPolicy;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import guestbook.Ting;


//import guestbook.HtmlFetcher;

//GBase











import com.google.api.gbase.client.*;
import com.google.gdata.data.*;
//import com.google.gdata.util.*;

@SuppressWarnings("serial")
public class ProcessTing extends HttpServlet {
	 
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String eM;
		if (user == null)
		{
			  	
			  eM = "test@example.com";
			  /*
			  //stop processing and return failure to the caller
			  //	application/json
			  resp.setContentType("text/html");
		      PrintWriter out = resp.getWriter();
		      String message = "User not logged in";
		      out.println(message);
		      return;
		      */
		        
		} else {
		
			eM = user.getEmail(); 
		}
		
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		javax.jdo.Query q = pm.newQuery(Ting.class);
		q.setFilter("uId == lastNameParam");
		q.setOrdering("tingId desc");
		q.declareParameters("String lastNameParam");
		 
		
		String jResult = "{"
				+ "\"status\": \"success\", "
				+ "\"data\": { "
				+ "\"dots\": [ ";
		

		try {
		  List<Ting> results = (List<Ting>) q.execute(eM);
		  if (!results.isEmpty()) {
		    for (Ting p : results) {
		      // Process result p
		    		Gson gson = new Gson();
		    		jResult += gson.toJson(p, Ting.class) + ","; 
			    //Ting t = gson.fromJson(jb.toString(), Ting.class);

		    }
		  } else {
		    // Handle "no results" case
		  }
		} finally {
		  q.closeAll();
		}
		
		
		/*
		//Get all tings for this user! 
		//Or get all tings where author == this user. 
		//Return the list as XML/JSON 
		Filter ff = new FilterPredicate("uId",
				                      FilterOperator.EQUAL,
				                      eM);
		Query q = new Query("Ting").setFilter(ff);
		// Use PreparedQuery interface to retrieve results
		// Get the Datastore Service
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);
		
		
		String jResult = "{"
				+ "\"status\": \"success\", "
				+ "\"data\": { "
				+ "\"dots\": [ ";
		

		for (Entity result : pq.asIterable()) {
		  
		  String address = (String) result.getProperty("address");
		  //String lastName = (String) result.getProperty("lastName");
		  //Long height = (Long) result.getProperty("height");
                   		  
		  jResult += "{ \"address\": \"" + address +   "\" },"; 
		 
		  
		}
		*/
		jResult = jResult.substring(0, jResult.length() - 1);
		jResult += "]"
				+ "}"
				+ "}";
		
		
		//jResult = "{\"status\": \"success\",  \"address\": \"375 Eleventh St, San Francisco, CA 94103\" }";
		
		
		resp.setContentType("application/json");
	    PrintWriter out = resp.getWriter();
	    out.print(jResult);
       
	}
	
	
	public void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws /*ServletException,*/ IOException {
		doPost (req, resp);
	}
	
	
	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws /*ServletException,*/ IOException {
		
		String reqUri = req.getRequestURI().toString();
		reqUri = reqUri.substring(1);
		int locationOfSlash = reqUri.indexOf('/');
		String tId = reqUri.substring(locationOfSlash + 1, reqUri.length());
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		     
		String author;
		if (user == null)
		{
			author = "test@example.com";
			
		}
		else {
			  author = user.getEmail();	
		}
		StringBuffer jb = new StringBuffer();
	    String line = null;
	    try {
	      BufferedReader reader = req.getReader();
	      while ((line = reader.readLine()) != null)
	        jb.append(line);
	    } catch (Exception e) { /*report an error*/ }
	    /*
	    Gson gson = new Gson();
	    Ting t = gson.fromJson(jb.toString(), Ting.class);
	    Long eTingId = t.getTingId();
		*/
		Long eTingId = new Long(Integer.parseInt(tId)); 
	    
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    if (eTingId != 0 && eTingId >= 1) {
	   	  	try {
	    	  		Ting e = pm.getObjectById(Ting.class, eTingId);
	             pm.deletePersistent(e);
	               
	   	  	} catch (IllegalStateException e) {
	            	
	            		//isUserLoggedIn = 0;
	            	
	        } finally {
	                pm.close();
	        }
	    }
	    //Else, Ting not found.
		    
	}
	
	//Handles both PUT and POST 
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws /*ServletException,*/ IOException {
		
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
		     
			String author;
			if (user == null)
			{
				author = "test@example.com";
				
			}
			else {
				  author = user.getEmail();	
			}
			StringBuffer jb = new StringBuffer();
		    String line = null;
		    try {
		      BufferedReader reader = req.getReader();
		      while ((line = reader.readLine()) != null)
		        jb.append(line);
		    } catch (Exception e) { /*report an error*/ }
		    Gson gson = new Gson();
		    Ting t = gson.fromJson(jb.toString(), Ting.class);
		    //TODO: Validate the author in t 
		    //Currently, deriving the author from the login credentials
		    t.setUId(author);
		      /* 
			  Albums albums = gson.fromJson(IOUtils.toString(new URL(url)), Albums.class);
			  try {
			    JSONObject jsonObject = JSONObject.fromObject(jb.toString());
			  } catch (ParseException e) {
			    // crash and burn
			    throw new IOException("Error parsing JSON request string");
			  }
			*/
			  // Work with the data using methods like...
			  // int someInt = jsonObject.getInt("intParamName");
			  // String someString = jsonObject.getString("stringParamName");
			  // JSONObject nestedObj = jsonObject.getJSONObject("nestedObjName");
			  // JSONArray arr = jsonObject.getJSONArray("arrayParamName");
			  // etc...
				
				//For the user, store the parameters sent get the list of Ting locations 
				//Return the list as XML/JSON 
				/*
				String addr = (req.getParameter("addr") != null) ?  req.getParameter("addr") : ""; 
				if (addr.length() <= 5) {
					resp.setContentType("text/html");
				    PrintWriter out = resp.getWriter();
				    //TODO: Needs to be JSON
				    String message = "Your address needs to be bigger";
				    out.println(message);
				    return;
				}
				*/
				
				 //TODO
                Long tLat = new Long(0); //getGeoCodeLang(addr); 
                Long tLong = new Long (0); //getGeoCodeLong(addr); 
                
				String nick = (req.getParameter("nick") != null) ? req.getParameter("nick") : "";
				String color = (req.getParameter("color") != null) ? req.getParameter("color") : "blue";
				String category = (req.getParameter("cat") != null) ? req.getParameter("cat") : "danceClub";
				
				
				Long newTingId = new Long(-1);
				//String tt = req.getParameter("eTingId"); 
				Long tt = t.getTingId();
				Long eTingId; 
				if ((tt != null) && (tt >= 1)) {
					eTingId = tt;
				} else {
					eTingId = new Long (0); 
				}
				//if ((tt != null) && (tt.length() > 0)) eTingId = new Long(Integer.parseInt(tt));
				User currentUser = user;
				Date date = new Date();
			    
				Ting tingToUpdateInGB = null;
		        List<Ting> tings;
		        //Ting Id has been specified and hence working on an existing Ting
		        //PUT use case

        			PersistenceManager pm = PMF.get().getPersistenceManager();
		        if (eTingId != 0 && eTingId >= 1) {
		        	  	/*
		        		try{
			        		javax.jdo.Query q = pm.newQuery(Ting.class);
			        		q.setFilter("tingId == dotId");
			        		q.declareParameters("Long dotId");
			        		List<Ting> results = (List<Ting>) q.execute(eTingId);
			        		if (!results.isEmpty()) {
			        			for (Ting p : results) {
			      		      p = t;
			      		      pm.makePersistent(p);
			      		    }
			      		}
		        		
		        		*/	
		        			try {
		                //Ting e = pm.getObjectById(Ting.class, eTingId.toString());
		        			Ting e = pm.getObjectById(Ting.class, eTingId);
		                
		                //if (!(e.getUId().equals(user.getEmail())))
		                //{
		                	//	throw new IllegalStateException( );
		                //}
		                
		                e = t; 
		                pm.makePersistent(e);
		                String addr = e.getAddress(); 
		                String iA = t.getAddress(); 
		                /*
			            	e.setTingLat(tLat); 
			        		e.setTingLong(tLong); 
			        		e.setAddress(addr); 
			        		e.setLocNickName(nick);
			        		e.setUId(author);
			        		e.setColor(color);
			        		e.setCategory(category);
			        		e.setLastModified(date);
			        		*/
		            } catch (IllegalStateException e) {
		            	
		            		//isUserLoggedIn = 0;
		            	
		            } finally {
		                pm.close();
		            }
		        } else { 
		    		//POST: Ting Id has not been specified and hence this is a new ting. 
		        	
		        		String counterQuery = " select from " + TingCounter.class.getName() ;
		        		PersistenceManager pmTC = PMF.get().getPersistenceManager();
		            List<TingCounter> tingCounter = (List<TingCounter>) pmTC.newQuery(counterQuery).execute();
		            
		            Long tId = new Long(1);
		            
		            if (tingCounter.isEmpty())
		            {
		            		TingCounter tc = new TingCounter(tId);
		            		try {
		                       pmTC.makePersistent(tc);
		                   } finally {
		                       pmTC.close();
		                   }
		            }
		            else
		            {
		            		TingCounter tc = tingCounter.get(0);
		            		tId = tc.getMaxUrlId() + 1; 
		            		tc.setMaxUrlId(tId);
		            		try {
		                      pmTC.makePersistent(tc);
		                  } finally {
		                      pmTC.close();
		                  }
		            	
		            }	
		        	
		            
		            //Ting newTing = new Ting (tId,  tLat, tLong, addr,  nick,  author, color, 
		    		    //     date, date, category);  
		            
		    			Ting newTing = new Ting(t, tId); 
		            newTingId = tId; //newTing.getTingId();
			        	PersistenceManager pmPost = PMF.get().getPersistenceManager();
			        	try {
			        		pmPost.makePersistent(newTing);
			               
			        	} finally {
			        		pmPost.close();
			        	}
		        	
		        		PersistenceManager pmU = PMF.get().getPersistenceManager();
		    			try {
		    				TUser e = pmU.getObjectById(TUser.class, author);
		    				e.addTingsCreated(newTing.getTingId());
		    				
		    			} catch (JDOObjectNotFoundException excp) {
		    				
		    				TUser e = new TUser(author, user.getNickname(), user, newTing.getTingId(), null);
		    				pmU.makePersistent(e);
		    			}
		    			
		    			finally {
		    				pmU.close();
		    			}
		        } 
		   	
		        //Need to send back Ajax response
		        resp.setContentType("application/json");
		        PrintWriter out = resp.getWriter();
		        //TODO: Return JSON 200 HTTP code. 
		        String message = "{\"id\":\"" + String.valueOf(newTingId) + "\"}";
		        out.print(message);
		        //Clear the cache 
		        MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		        syncCache.clearAll();
		
	}

}
