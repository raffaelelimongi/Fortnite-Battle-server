package business;

import java.util.List;
import org.json.JSONException;
import domain.Notizia;

public interface NotizieService {
	
	List<Notizia> findallNotizie(String language) throws JSONException;

}
