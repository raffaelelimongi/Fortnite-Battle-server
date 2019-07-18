package util;


import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;
import javax.enterprise.inject.Produces;

public class LoggerProducer {
	
	@Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
}

}
