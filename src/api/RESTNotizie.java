package api;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import business.NotizieService;
import business.impl.NotizieServiceImpl;
import domain.Notizia;
import filter.JWTTokenNeeded;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;



@Path("/notizie")
@JWTTokenNeeded
public class RESTNotizie {
	
	private NotizieService newsservice = new NotizieServiceImpl();
	
	@GET
	@Consumes("application/json")
	@Produces("application/json")
	public List<Notizia> getAllNotizie(@QueryParam ("language") String language){
			
		try {
		return newsservice.findallNotizie(language);
			
			/* String json = "";
		        ObjectMapper Obj = new ObjectMapper();
		        try {
					json = Obj.writeValueAsString(n);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        return Response.ok(json).build();*/
		}catch(Exception e) {
			e.printStackTrace();

		}
		return null;
	}
	
}
