package business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import domain.Classifica;
import domain.Torneo;
import domain.User;

public interface TorneiService {
	
	Integer createTorneo(Torneo tor) throws SQLException;
	Torneo findByid(String id)throws SQLException;
	List<Torneo> getAllTournaments() throws SQLException;
	Classifica getLeaderboard(String id) throws SQLException;
	void refreshLeaderboard(String id,Map<String,String> mapuser) throws SQLException;
	Integer joinTorneo(String user,String idtor) throws SQLException;
	Integer refreshClassificheBackground() throws SQLException;
}
