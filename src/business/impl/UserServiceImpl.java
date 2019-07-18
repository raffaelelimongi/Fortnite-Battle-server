package business.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Connection;
import business.UserService;
import domain.Arma;
import domain.User;

public class UserServiceImpl implements UserService{
	
	 private PreparedStatement ps;
	 
	 private ArrayList<User> listuser = new ArrayList<>();
	 
	 private ResultSet resultSet;
	 
	 private static HttpURLConnection connection;

	 
	 @Override
	 public User authuser(String user, String pass) throws SQLException, ClassNotFoundException {
		 
		 ConnectionClass connectionClass = new ConnectionClass();
		 Connection conn = connectionClass.getConnection();
		 
		 String sql = "SELECT * FROM user  WHERE  username=? AND password =?";
		
		 ps=conn.prepareStatement(sql);
		 ps.setString(1, user);
		 ps.setString(2, pass);
		 
		 resultSet = ps.executeQuery();
		 
		 if(resultSet.next()) {
		 
		 String id = resultSet.getString("iduser");
 		 String nome = resultSet.getString("nome");
 		 String email = resultSet.getString("email");
 		 String bio = resultSet.getString("bio");
 		 String datanascita = resultSet.getString("datanascita");
 		 String avatar = resultSet.getString("avatar");
 		 return new User(id,user,pass,nome,email,bio,datanascita, avatar);
		 }
		 else {
			 return null;
		 }

		}
	 
	 
	@Override
	public ArrayList<User> findByid(String id) throws SQLException, ClassNotFoundException {
		
		ConnectionClass connectionClass = new ConnectionClass();
		Connection conn = connectionClass.getConnection();
		
		String sql="SELECT * FROM user WHERE  iduser=?";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		
		ResultSet resultset = ps.executeQuery();
		
		while(resultset.next()) {
			
			listuser.add(new User("",resultset.getString("username"),resultset.getString("password"),"","","","",""));	
		}
		return listuser;
	}


	@Override
	public ArrayList<User> findAllUser() throws SQLException, ClassNotFoundException {
		
		ConnectionClass connectionClass = new ConnectionClass();
		Connection conn = connectionClass.getConnection();
		
		String sql="SELECT * FROM user";
		
		ps = conn.prepareStatement(sql);
		ResultSet resultset = ps.executeQuery();
		
		while(resultset.next()) {
			listuser.add(new User("",resultset.getString("username"),resultset.getString("password"),"","","","",""));	
			}
		return listuser;
	}


	@Override
	public Integer createUser(User user) throws SQLException {
		ConnectionClass connectionClass = new ConnectionClass();
		Connection conn = connectionClass.getConnection();
		
		String sql= "INSERT INTO user(username,password,email,datanascita) VALUES (?,?,?,?)";
		ps = conn.prepareStatement(sql);
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getPassword());
		ps.setString(3, user.getEmail());
		ps.setString(4, user.getDataNascita());
		
		return ps.executeUpdate();

	}


	@Override
	public Integer updateUser(User user) throws SQLException {
		ConnectionClass connectionClass = new ConnectionClass();
		Connection conn = connectionClass.getConnection();
		
		String sql = "UPDATE user SET username=?, nome=?, email=?, bio=?, datanascita =?, avatar=? WHERE iduser=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getNome());
		ps.setString(3, user.getEmail());
		ps.setString(4, user.getBio());
		ps.setString(5, user.getDataNascita());
		ps.setString(6, user.getAvatar());
		ps.setString(7, user.getID());
				
		return ps.executeUpdate();
	}


	@Override
	public String getUserid(String u) {
		
		String key="4285eaaa-f25e-44fd-a687-4b55fc277793";
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		String userid = "";
		
		
		try {
			URL url = new URL("https://api.fortnitetracker.com/v1/profile/pc/" + u);
			connection = (HttpURLConnection) url.openConnection();
		
			connection.setRequestMethod("GET");
			
			//request header
			connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			connection.addRequestProperty("TRN-Api-Key", key);
			
			connection.connect();
			int status = connection.getResponseCode();
			
			System.out.println(status);			
		
			if(status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);					
				}
				reader.close();
			}else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);					
				}
				reader.close();
			}
			System.out.println(responseContent.toString());
				
			    JSONObject jsonObject = new JSONObject(responseContent.toString());
			    
			    userid = jsonObject.getString("accountId").replaceAll("-","");
			    
		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			connection.disconnect();
		}
		
		return userid;
	}


	@Override
	public Map<String, Integer> getMatches(String uid) {
		
		String key="4285eaaa-f25e-44fd-a687-4b55fc277793";
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		Map<String,Integer> matchuser = new HashMap();
		List<Integer> kill = new ArrayList<>();
		Integer i =0, killm=0,nmatch=0,nscore=0;
		
		try {
			URL url = new URL("https://api.fortnitetracker.com/v1/profile/account/"+uid+"/matches ");
			connection = (HttpURLConnection) url.openConnection();
		
			connection.setRequestMethod("GET");
			
			//request header
			connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			connection.addRequestProperty("TRN-Api-Key", key);
			
			connection.connect();
			int status = connection.getResponseCode();
			
			System.out.println(status);			
		
			if(status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);					
				}
				reader.close();
			}else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);					
				}
				reader.close();
			}		
			
			 	System.out.println(responseContent.toString());
			    JSONArray jsonarray = new JSONArray(responseContent.toString());
			    
			    while(i<jsonarray.length() && nmatch<10) {
			    	JSONObject datavalue = jsonarray.getJSONObject(i);
			    	
			    	 killm += datavalue.getInt("kills");
			    	 nmatch += datavalue.getInt("matches");
			    	 nscore += datavalue.getInt("score");
			    	
			    	 i++;
			    }
			    
			    matchuser.put("Matches", nmatch);
			    matchuser.put("Kills", killm);
			    matchuser.put("Score", nscore);
			    
		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			connection.disconnect();
		}
		
		return matchuser;
	}
	
}
