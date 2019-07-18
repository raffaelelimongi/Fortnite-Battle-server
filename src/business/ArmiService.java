package business;

import java.util.List;
import org.json.JSONException;
import domain.Arma;

public interface ArmiService {
	
	List<Arma> findAllWeapons() throws JSONException;

}
