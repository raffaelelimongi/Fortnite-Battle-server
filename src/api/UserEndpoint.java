package api;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import domain.Account;
import domain.User;
import filter.JWTTokenNeeded;
import util.KeyGenerator;
import util.PasswordUtils;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.Connection;

import business.UserService;
import business.impl.UserServiceImpl;

import java.security.Key;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Transactional
public class UserEndpoint {
	
	@Context
    private UriInfo uriInfo;

	/*
	 * @Inject
    private Logger logger;*/

    @Inject
    private KeyGenerator keyGenerator;
    
    private UserService userservice = new UserServiceImpl();
    
    private List<User> listuser = new ArrayList<User>();
 
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Account acc) {
    	
    	String user=acc.getUsername();
    	String password=acc.getPassword();
    	User userdb = new User();

        try {

           // logger.info("#### login/password : " + login + "/" + password);

            // Authenticate the user using the credentials provided
        	userdb = authenticate(user, password);

            // Issue a token for the user
            String token = issueToken(user);
            
            System.out.println("ECCO IL TOKEN" + token);
            
            //Convert userdb to JSON
            String resp = JSONConvert(userdb);

            // Return the token on the response
            return Response.ok().header("Access-Control-Expose-Headers","authorization")
            		.header(AUTHORIZATION, "Bearer " + token).entity(resp).type(MediaType.APPLICATION_JSON).build();

        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private User authenticate(String login, String password) throws Exception {

    	User utemp = userservice.authuser(login, password);
    	
    	if(utemp != null) {
    		return utemp;
    		
		}else {
			throw new SecurityException("Invalid user/password");
		}
       
    }

    private String issueToken(String login) {
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusDays(1l)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
      //  logger.info("#### generating token for a key : " + jwtToken + " - " + key);
        
        System.out.println(toDate(LocalDateTime.now().plusDays(1l)));
        return jwtToken;

    }
    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) throws SQLException, ParseException {
    	
    	//setto la password passata tramite il campo bio
    	user.setPassword(user.getBio());
    	
    	Integer result = userservice.createUser(user);
    	
    	if (result == 1) {
    		return Response.created(uriInfo.getAbsolutePathBuilder().path(user.getUsername()).build()).build();
        } else {
            throw new SecurityException("Utente gia presente nel sistema!!");
        }

    }
    
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response updateUser(User user) throws SQLException {
    	
    	Integer result = userservice.updateUser(user);
    	
    	System.out.println(user.getID());
    	
    	if (result == 1) {
    		String resp = JSONConvert(user);
    		return Response.ok(uriInfo.getAbsolutePathBuilder().path(user.getUsername()).build()).entity(resp).
    				type(MediaType.APPLICATION_JSON).build();
        } else {
        	return Response.notModified(user.getUsername()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response findByid(@PathParam("id") String id) throws SQLException {
    	
    	try {
			listuser = userservice.findByid(id);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(listuser.isEmpty()) {
    		return Response.status(NOT_FOUND).build();
    	}
    	
    	 return Response.ok(listuser).build();
    }
    
    @GET
    public Response findAllUser() throws SQLException {
    	
    	try {
			listuser = userservice.findAllUser();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(listuser.isEmpty()) {
    		return Response.status(NOT_FOUND).build();
    	}
    	
    	 return Response.ok(listuser).build();
    	
    }
    
    @GET
    @Path("/idplayer")
    public Response findIdPlayer(@QueryParam("user") String user) throws SQLException{
    	
    	System.out.println(user);
    	String idplayer = userservice.getUserid(user);
    	
    	System.out.println("IDPLAYER" + idplayer);
    	

    	if(idplayer != null) {
    		
    		String json = "";
            ObjectMapper Obj = new ObjectMapper();
          
    		try {
    			json = Obj.writeValueAsString(idplayer);
    		} catch (JsonProcessingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		return Response.ok(json).build();		
    	}
    	
		return Response.status(INTERNAL_SERVER_ERROR).build();
    	
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
}
    
    private String JSONConvert(User u) {
        String json = "";
        ObjectMapper Obj = new ObjectMapper();
        try {
			json = Obj.writeValueAsString(u);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return json;
    	
    }

}
