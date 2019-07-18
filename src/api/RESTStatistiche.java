package api;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import business.StatisticheService;
import business.impl.StatisticheServiceImpl;
import domain.Statistiche;
import filter.JWTTokenNeeded;

@Path("statistiche")
@JWTTokenNeeded
public class RESTStatistiche {
	
	private StatisticheService statsservice = new StatisticheServiceImpl();
	
	@GET
	@Consumes("application/json")
	@Produces("application/json")
	public Response getAllStats(@QueryParam("id") String id){
		
		List<Statistiche> allstats = new ArrayList<Statistiche>();
		
		allstats.add(statsservice.findOverallStats(id));
		allstats.add(statsservice.findSoloStats(id));
		allstats.add(statsservice.findDuoStats(id));
		allstats.add(statsservice.findSquadStats(id));
		
		 String json = "";
	        ObjectMapper Obj = new ObjectMapper();
	        try {
				json = Obj.writeValueAsString(allstats);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		if(allstats.isEmpty()) {
			return Response.status(NOT_FOUND).build();
		}
		return Response.ok(json).build();
	}
	
	
	@GET
	@Path("overall")
	@Consumes("application/json")
	@Produces("application/json")
	public Statistiche getOverallStats(@QueryParam("id") String id){
		
		try {
			return statsservice.findOverallStats(id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
	
	@GET
	@Path("solo")
	@Consumes("application/json")
	@Produces("application/json")
	public Statistiche getSoloStats(@QueryParam("id") String id){
		
		try {
			return statsservice.findSoloStats(id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
	
	@GET
	@Path("duo")
	@Consumes("application/json")
	@Produces("application/json")
	public Statistiche getDuoStats(@QueryParam("id") String id){
		
		try {
			return statsservice.findDuoStats(id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
	
	@GET
	@Path("squad")
	@Consumes("application/json")
	@Produces("application/json")
	public Statistiche getSquadStats(@QueryParam("id") String id){
		
		try {
			return statsservice.findSquadStats(id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}

}
