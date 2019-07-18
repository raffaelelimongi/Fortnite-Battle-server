package business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import domain.User;

public interface UserService {
	
	ArrayList<User> findByid(String id) throws SQLException,ClassNotFoundException;
	User authuser(String user, String pass) throws SQLException,ClassNotFoundException;
	ArrayList<User> findAllUser() throws SQLException,ClassNotFoundException;
	Integer createUser(User user) throws SQLException;
	Integer updateUser(User user) throws SQLException;
	String getUserid(String user) throws JSONException;
	Map<String,Integer> getMatches(String uid);
}
