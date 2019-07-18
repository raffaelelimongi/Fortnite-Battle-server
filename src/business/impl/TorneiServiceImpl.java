package business.impl;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Connection;
import business.TorneiService;
import business.UserService;
import domain.Classifica;
import domain.Torneo;

public class TorneiServiceImpl implements TorneiService{
	
	private PreparedStatement ps;
	
	private ArrayList<Torneo> listtor= new ArrayList<>();

	@Override
	public Integer createTorneo(Torneo tor) throws SQLException {
		ConnectionClass connectionClass = new ConnectionClass();
		Connection conn = connectionClass.getConnection();
		
		String sql= "INSERT INTO torneo(nome,descrizionetor,datainizio,durata,usernametor,premio1,premio2,premio3,partecipanti) VALUES (?,?,?,?,?,?,?,?,1)";
		ps = conn.prepareStatement(sql);
		ps.setString(1, tor.getNome());
		ps.setString(2, tor.getDescrizione());
		ps.setString(3, tor.getDatainizio());
		ps.setString(4, tor.getDurata());
		ps.setString(5, tor.getUsername());
		ps.setString(6, tor.getPremio1());
		ps.setString(7, tor.getPremio2());
		ps.setString(8, tor.getPremio3());
		ps.executeUpdate();
		
		int result = addUserTorneoCreate(tor);
		
		return result;
	}
	
	private Integer addUserTorneoCreate(Torneo tor) throws SQLException {
		ConnectionClass connectionClass = new ConnectionClass();
		Connection conn = connectionClass.getConnection();
		
		String sql1= "INSERT INTO user_torneo(username,idtorneo) VALUES (?,(SELECT idtorneo from torneo WHERE usernametor=? AND datainizio=?))";
		ps = conn.prepareStatement(sql1);
		ps.setString(1, tor.getUsername());
		ps.setString(2, tor.getUsername());
		ps.setString(3, tor.getDatainizio());
		
		return ps.executeUpdate();
	}

	@Override
	public Torneo findByid(String id) throws SQLException {
		ConnectionClass connectionClass = new ConnectionClass();
		Connection conn = connectionClass.getConnection();
		
		String sql="SELECT * FROM torneo WHERE idtorneo=?";
		ps = conn.prepareStatement(sql);
		
		ps.setString(1,id);
		
		ResultSet resultset = ps.executeQuery();
		
		while(resultset.next()) {
			String nome = resultset.getString("nome");
			String desc = resultset.getString("descrizionetor");
			String dateinizio = resultset.getString("datainizio");
			Integer partecipanti = resultset.getInt("partecipanti");
			String durata = resultset.getString("durata");
			String regolamento = resultset.getString("regolamento");
			String premio1 = resultset.getString("premio1");
			String premio2 = resultset.getString("premio2");
			String premio3 = resultset.getString("premio3");
			String user = resultset.getString("usernametor");
			return new Torneo(id,nome,desc,dateinizio,partecipanti,durata,regolamento,premio1,premio2,premio3,user);
		}
		return null;
	}

	@Override
	public List<Torneo> getAllTournaments() throws SQLException {
		
		ConnectionClass connectionClass = new ConnectionClass();
		Connection conn = connectionClass.getConnection();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Calendar c = Calendar.getInstance(); 
		String durata = "";
		
		ArrayList<Torneo> listtornei = new ArrayList<>();

		String sql="SELECT * FROM torneo order by datainizio";
		ps = conn.prepareStatement(sql);
		
		ResultSet resultset = ps.executeQuery();
		
		while(resultset.next()) {
			String id = resultset.getString("idtorneo");
			String nome = resultset.getString("nome");
			String desc = resultset.getString("descrizionetor");
			String dateinizio = resultset.getString("datainizio");
			Integer partecipanti = resultset.getInt("partecipanti");
			
			//torno la data di fine torneo in durata
			String dtemp = resultset.getString("durata");
			try {
				Date d = dateFormat.parse(dateinizio);
				c.setTime(d);
			    c.add(Calendar.HOUR, Integer.parseInt(dtemp));
			    Date temp = c.getTime();
				durata = dateFormat.format(temp);
				System.out.println(durata);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String premio1 = resultset.getString("premio1");
			String premio2 = resultset.getString("premio2");
			String premio3 = resultset.getString("premio3");
			String username = resultset.getString("usernametor");

			listtornei.add(new Torneo(id,nome,desc,dateinizio,partecipanti,durata,"",premio1,premio2,premio3,username));
		}		
		
		return listtornei;
	}

	@Override
	public Classifica getLeaderboard(String id) throws SQLException {
		ConnectionClass connectionClass = new ConnectionClass();
		Connection conn = connectionClass.getConnection();
		List<Integer> m= new ArrayList<Integer>();
		List<Integer> p= new ArrayList<Integer>();
		List<String> us = new ArrayList<String>();
		
		String sql="SELECT ut.username,ut.puntit,ut.matcht FROM user_torneo ut JOIN user u ON(ut.username=u.username) JOIN torneo t ON(ut.idtorneo=t.idtorneo) WHERE  ut.idtorneo=? order by ut.puntit DESC";
		ps = conn.prepareStatement(sql);
		
		ps.setString(1,id);
		
		ResultSet resultset = ps.executeQuery();
		
		while(resultset.next()) {
			String usernamelead = resultset.getString("ut.username");
			Integer puntilead = resultset.getInt("ut.puntit");
			Integer match = resultset.getInt("ut.matcht");
			m.add(match);
			p.add(puntilead);
			us.add(usernamelead);
		}
		return new Classifica(us,p,m);
	}

	@Override
	public void refreshLeaderboard(String id,Map <String,String> mapuser) throws SQLException {
		UserService userservice = new UserServiceImpl();
		Map<String,Integer> mapResponse = new HashMap();
		List<Map<String,Integer>> listleader = new ArrayList<>();
		
		ConnectionClass connectionClass = new ConnectionClass();
		Connection conn = connectionClass.getConnection();
		String sql = "UPDATE user_torneo SET killt=?,puntit=?, matcht=? WHERE idtorneo=? AND username=?";
		ps = conn.prepareStatement(sql);
		
		for (Map.Entry<String, String> entry : mapuser.entrySet()) {
			String valuser = entry.getValue();
			String userkey = entry.getKey();
			mapResponse = userservice.getMatches(valuser);
			
			Integer killt = mapResponse.get("Kills");
			Integer puntit = mapResponse.get("Score");
			Integer matcht = mapResponse.get("Matches");

			ps.setInt(1, killt);
		    ps.setInt(2, puntit);
		    ps.setInt(3, matcht);
		    ps.setString(4, id);
		    ps.setString(5, userkey);
		    ps.executeUpdate();

		}
	}

	@Override
	public Integer joinTorneo(String user,String idtor) throws SQLException {
		ConnectionClass connectionClass = new ConnectionClass();
		Connection conn = connectionClass.getConnection();
		Integer r2 = 0;
		
		String sql= "INSERT INTO user_torneo(username,idtorneo) VALUES (?,?)";
		ps = conn.prepareStatement(sql);
		ps.setString(1, user);
		ps.setString(2, idtor);
		
		Integer r1 = ps.executeUpdate();
		if(r1 == 1) {
			r2 = updateTor(idtor);
			
		}
		conn.close();
		return r2;
	}
	
	public Integer updateTor(String idtor) throws SQLException {
		ConnectionClass connectionClass = new ConnectionClass();
		Connection conn = connectionClass.getConnection();
		
		String sql1 = "UPDATE torneo SET partecipanti=partecipanti+1 WHERE idtorneo=?";
		ps = conn.prepareStatement(sql1);
		ps.setString(1, idtor);
		
		Integer r2 = ps.executeUpdate();
		
		conn.close();
		
		return r2;
		
	}

	@Override
	public Integer refreshClassificheBackground() throws SQLException {
		ConnectionClass connectionClass = new ConnectionClass();
		Connection conn = connectionClass.getConnection();
		
		ArrayList<String> listidtor = new ArrayList<>();
		ArrayList<String> userlistor = new ArrayList<>();
		Map<String,Integer> matchuser = new HashMap();
		Integer i = 0;
		
		String a= "";
		String b = "";
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = new Date();
		Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, -24);
        Date temp = c.getTime();
        
		a = dateFormat.format(date);
		
		b = dateFormat.format(temp);
		
		UserService userservice = new UserServiceImpl();
		
		String sql = "SELECT idtorneo FROM torneo WHERE datainizio BETWEEN ? AND ?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, b);
		ps.setString(2, a);
		
		ResultSet r = ps.executeQuery();
		
		while(r.next()) {
			listidtor.add(r.getString("idtorneo"));
		}
		
		for(String t : listidtor) {
			
			String sql2 = "SELECT * FROM user_torneo WHERE idtorneo=?";
			ps = conn.prepareStatement(sql2);
			ps.setString(1, t);
			
			ResultSet r1 = ps.executeQuery();
			
			while(r1.next()) {
				userlistor.add(r1.getString("username"));
			}
			
			for(String usertemp: userlistor) {
			System.out.println("USER:" + usertemp);
			
			String uid = userservice.getUserid(usertemp);
			
			matchuser = userservice.getMatches(uid);
			
			String sql3 = "UPDATE user_torneo SET puntit=?, killt=?, matcht=? WHERE idtorneo=? AND username=?";
			ps = conn.prepareStatement(sql3);
			
			ps.setInt(1, matchuser.get("Score"));
			ps.setInt(2, matchuser.get("Kills"));
			ps.setInt(3, matchuser.get("Matches"));
			ps.setString(4, t);
			ps.setString(5, usertemp);
			
			i = ps.executeUpdate();
			
			}		
		}
		return i;
	}

}
