package api;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.json.JSONException;
import business.ArmiService;
import business.impl.ArmiServiceImpl;
import domain.Arma;
import filter.JWTTokenNeeded;

@Path("armi")
@JWTTokenNeeded
public class RESTArmi {
	
	private ArmiService armiservice = new ArmiServiceImpl();
	
	@GET
	@Consumes("application/json")
	@Produces("application/json")
	public List<Arma> getAllWeapons(){
		
		try {
			return armiservice.findAllWeapons();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}

}
