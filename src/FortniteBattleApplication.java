
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import Provider.CorsFilter;
import filter.JWTTokenNeeded;
import filter.JWTTokenNeededFilter;
import util.ApplicationBinder;
import util.LoggerProducer;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class FortniteBattleApplication extends ResourceConfig {
	
	public FortniteBattleApplication() {
		
		packages("api");
		
		register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
				Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, 10000));
		
		register(JacksonJsonProvider.class);
		
		register(new ApplicationBinder());  
		
		register(JWTTokenNeededFilter.class);
		
		register(CorsFilter.class);
		
		//run
		new MyTaskExecutor().startExecutionAt(14, 4, 0);
	}

}
