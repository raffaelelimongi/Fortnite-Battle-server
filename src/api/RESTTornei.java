package api;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import business.TorneiService;
import business.UserService;
import business.impl.TorneiServiceImpl;
import business.impl.UserServiceImpl;
import domain.Classifica;
import domain.Torneo;
import domain.User;
import filter.JWTTokenNeeded;

@Path("/tornei")
@JWTTokenNeeded
public class RESTTornei {
	
	@Context
    private UriInfo uriInfo;
	
	private TorneiService torneiservice = new TorneiServiceImpl();
	
	private List<Torneo> listtornei = new ArrayList<>();
	
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response createTorneo(Torneo torneo) throws SQLException{
		
    	String regolamento = "Scoring System:  Victory Royale: 10 points, 2nd-5th: 7 points, 6th-15th: 5 points, 16th-25th: 3 points, Eache Elimination: 1 point'";
    	
    	torneo.setRegolamento(regolamento);
    	
    	Integer result = torneiservice.createTorneo(torneo);
    	
    	if (result == 1) {
    		return Response.created(uriInfo.getAbsolutePathBuilder().path(torneo.getNome()).build()).build();
        } else {
            throw new SecurityException("Torneo gia presente nel sistema!!");
        }
	}
	
	@GET
	@Path("{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response findByid(@PathParam("id") String id) throws SQLException{
		
		Torneo tor = torneiservice.findByid(id);
		
		if(tor == null) {
			return Response.status(NOT_FOUND).build();
		}
		return Response.ok(tor).build();
	}
	
	@GET
	@Consumes("application/json")
	@Produces("application/json")
	public Response getAllTournaments() throws SQLException{
		
		listtornei = torneiservice.getAllTournaments();
		
		String response = JSONConvert(listtornei);
		
		if(listtornei.isEmpty()) {
			return Response.status(NOT_FOUND).build();
		}
		return Response.ok(response).build();
	}
	
	@GET
	@Path("/classifica")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getLeaderboardTor(@QueryParam ("idtor") String id) throws SQLException{
		
		Classifica classifica;
		
		classifica = torneiservice.getLeaderboard(id);
			
		String json = "";
        ObjectMapper Obj = new ObjectMapper();
        try {
			json = Obj.writeValueAsString(classifica);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		if(classifica == null) {
			return Response.status(NOT_FOUND).build();
		}
		return Response.ok(json).build();
	}
	
	@POST
	@Path("/join/{join}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response joinTournament(@PathParam("join") String idtor, String user) throws SQLException {
		
		System.out.println("USERNAME:" + user);
		System.out.println("IDTORNEO:" + idtor);
		
		Integer result = torneiservice.joinTorneo(user, idtor);
	
		if (result == 1) {
			return Response.created(uriInfo.getAbsolutePathBuilder().build()).build();
        } else {
            throw new SecurityException("Internal Server Error!!");
        }
		
	}
	
	  private String JSONConvert(List<Torneo> t) {
	        String json = "";
	        ObjectMapper Obj = new ObjectMapper();
	        try {
				json = Obj.writeValueAsString(t);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
			return json;
	    	
	    }

}
