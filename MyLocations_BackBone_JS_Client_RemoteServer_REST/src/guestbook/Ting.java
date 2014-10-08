package guestbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
//import org.datanucleus.api.jpa.annotations.Extension;





import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

import javax.persistence.Temporal;
import javax.persistence.Lob;

import com.google.gson.Gson;
 
/* 
 * Uber
 * id - ? String 
 * lat: 
*  lng:
address (e.g. 800 Market Street, San Francisco, CA 94114)
name (e.g. Work)
 */

@PersistenceCapable(identityType = IdentityType.APPLICATION)
//@Searchable
public class Ting {

    @Persistent
    @SearchableId
    @PrimaryKey
    private Long tingId; 

	@Persistent
    @SearchableProperty
    private Long tingLat;

    @Persistent
    @SearchableProperty
    private Long tingLong;
	
    @Persistent
    @SearchableProperty
    private String address;
    
    //String limit on GAE = 500 chars
    @Persistent
    @SearchableProperty
    private String locNickName;
    
    //uId or unique Id. Need not be an email Id - an id specific to the user
    @Persistent
    @SearchableProperty
    private String uId;
    
    @Persistent
    @SearchableProperty
    private String color; 
    
    @Persistent
    private String category;
  
    @Persistent
    //@SearchableProperty(format="yyyy-mm-dd")
    private Date createdDate;
    
    @Persistent
    //@SearchableProperty(format="yyyy-mm-dd")
    private Date lastModified;
    
    /*
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
    */
   

    
	
	public Ting (Long tingId,  Long vLat, Long vLong, String vAddress, String nick,  String author, String vColor, 
    		         Date createdDate, Date lastModified,  
    			     String vCategory) {
        
		this.setTingId(tingId); 
		this.setTingLat(vLat); 
		this.setTingLong(vLong); 
		this.setAddress(vAddress); 
		this.setLocNickName(nick);
		this.setUId(author);
		this.setColor(vColor);
		this.setCategory(vCategory);
		this.setCreatedDate(createdDate); 
		this.setLastModified(lastModified);

    }

	
	public Ting (Ting t, Long tId) {

		this.setTingId(tId); 
		this.setTingLat(t.getTingLat()); 
		this.setTingLong(t.getTingLong()); 
		this.setAddress(t.getAddress()); 
		this.setLocNickName(t.getLocNickName());
		this.setUId(t.getUId());
		this.setColor(t.getColor());
		this.setCategory(t.getCategory());
		this.setCreatedDate(new Date()); 
		this.setLastModified(new Date());

}
	
	
	
    public Long getTingId() {
		return tingId;
	}

	public void setTingId(Long tingId) {
		this.tingId = tingId;
	}



	public Long getTingLat() {
		return tingLat;
	}



	public void setTingLat(Long tingLat) {
		this.tingLat = tingLat;
	}



	public Long getTingLong() {
		return tingLong;
	}



	public void setTingLong(Long tingLong) {
		this.tingLong = tingLong;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getLocNickName() {
		return locNickName;
	}



	public void setLocNickName(String locNickName) {
		this.locNickName = locNickName;
	}



	public String getUId() {
		return uId;
	}



	public void setUId(String uId) {
		this.uId = uId;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Date getLastModified() {
		return lastModified;
	}



	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
/*
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

*/

	public String getColor() {
		return color;
	}



	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public void setColor(String tingColor) {
		this.color = tingColor;
	}

	//To convert a string into an escaped string - do this: 
	//Eclipes - Preferences - Windows (?) - Editors - Java - tick the option to escape when pasted in quotes. 
	
	/*
	 * The following function is for efficiency 
	 * It is to convert an entity to a Ting 
	 * You get a list of entities when you execute a query on Keys - see TingTags/SearchModule 
	 */
	/*
	public Ting (Entity entity) {
		  // TODO get properties from entity and populate POJO
		  this.titles=(List<String>) entity.getProperty("titles");
		  this.imageUrls = (List<String>) entity.getProperty("imageUrls");
		  //get the key
		  //if the @PrimaryKey is a Long use this
		  this.key=entity.getKey();
		  //if the @PrimaryKey is a String use this
		  //this.id=entity.getKey().getName();
	}
	*/


	
} //end of class

