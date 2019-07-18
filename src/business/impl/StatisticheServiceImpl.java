package business.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import business.StatisticheService;
import domain.Arma;
import domain.Statistiche;

public class StatisticheServiceImpl implements StatisticheService{
	
	private String key="51ec729be5d23f5051b16e5baef90339";
	private static HttpURLConnection connection;
	BufferedReader reader;
	String line;
	StringBuffer responseContent = new StringBuffer();
    List<Statistiche> list = new ArrayList<Statistiche>();

	@Override
	public Statistiche findOverallStats(String id) throws JSONException {
		Statistiche ovstats = null;
		try {
			URL url = new URL("https://fortnite-api.theapinetwork.com/prod09/users/public/br_stats?user_id=" + id + "&platform=pc");
			connection = (HttpURLConnection) url.openConnection();
		
			connection.setRequestMethod("GET");
			
			//request header
			connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			connection.addRequestProperty("Authorization", key);
			
			connection.connect();
			int status = connection.getResponseCode();
			
			System.out.println(status);			
		
			if(status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);					
				}
				reader.close();
			}else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);					
				}
				reader.close();
			}
			System.out.println(responseContent.toString());
			
				JSONObject jsonObject = new JSONObject(responseContent.toString());
			    System.out.println("JSONARRAY:"+jsonObject);
				
			   // JSONObject jsonObject = new JSONObject(responseContent.toString());
			    JSONObject total = (JSONObject) jsonObject.get("totals");
			    System.out.println("TOTAL:"+ total);
			    
			    Integer matches = total.getInt("matchesplayed");
			    Integer win = total.getInt("wins");
			    Integer kill = total.getInt("kills");
			    Double score = total.getDouble("score");
			    Double kd = total.getDouble("kd");
			    Double winrate = total.getDouble("winrate");
			    
			    ovstats = new Statistiche(matches,kill,win,score,0,0,"overall",kd,winrate);	    

		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			connection.disconnect();
		}
		return ovstats;
	}

	@Override
	public Statistiche findSoloStats(String id) throws JSONException {
		Statistiche solostats = null;
		// TODO Auto-generated method stub
		try {
			URL url = new URL("https://fortnite-api.theapinetwork.com/prod09/users/public/br_stats?user_id=" + id + "&platform=pc");
			connection = (HttpURLConnection) url.openConnection();
		
			connection.setRequestMethod("GET");
			
			//request header
			connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			connection.addRequestProperty("Authorization", key);
			
			connection.connect();
			int status = connection.getResponseCode();
			
			System.out.println(status);			
		
			if(status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);					
				}
				reader.close();
			}else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);					
				}
				reader.close();
			}
			System.out.println(responseContent.toString());
				
				JSONObject jsonObject = new JSONObject(responseContent.toString());
				System.out.println("JSONARRAY:"+jsonObject);
			
				JSONObject total = (JSONObject) jsonObject.get("stats");
				System.out.println("TOTAL:"+ total);
			    
			    Integer matches = total.getInt("matchesplayed_solo");
			    Integer win = total.getInt("placetop1_solo");
			    Integer kill = total.getInt("kills_solo");
			    Integer top10 = total.getInt("placetop10_solo");
			    Integer top25 = total.getInt("placetop25_solo");
			    Double score = total.getDouble("score_solo");
			    Double kd = total.getDouble("kd_solo");
			    Double winrate = total.getDouble("winrate_solo");
			    
			    solostats = new Statistiche(matches,kill,win,score,top25,top10,"solo",kd,winrate);	    

		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			connection.disconnect();
		}
		return solostats;
	}

	@Override
	public Statistiche findDuoStats(String id) throws JSONException {
		Statistiche duostats = null;
		// TODO Auto-generated method stub
		try {
			URL url = new URL("https://fortnite-api.theapinetwork.com/prod09/users/public/br_stats?user_id=" + id + "&platform=pc");
			connection = (HttpURLConnection) url.openConnection();
		
			connection.setRequestMethod("GET");
			
			//request header
			connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			connection.addRequestProperty("Authorization", key);
			
			connection.connect();
			int status = connection.getResponseCode();
			
			System.out.println(status);			
		
			if(status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);					
				}
				reader.close();
			}else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);					
				}
				reader.close();
			}
			System.out.println(responseContent.toString());
				
			    JSONObject jsonObject = new JSONObject(responseContent.toString());
			    JSONObject total = (JSONObject) jsonObject.get("stats");
			    
			    Integer matches = total.getInt("matchesplayed_duo");
			    Integer win = total.getInt("placetop1_duo");
			    Integer kill = total.getInt("kills_duo");
			    Integer top10 = total.getInt("placetop5_duo");
			    Integer top25 = total.getInt("placetop12_duo");
			    Double score = total.getDouble("score_duo");
			    Double kd = total.getDouble("kd_duo");
			    Double winrate = total.getDouble("winrate_duo");
			    
			    duostats = new Statistiche(matches,kill,win,score,top25,top10,"duo",kd,winrate);	    

		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			connection.disconnect();
		}
		return duostats;
	}

	@Override
	public Statistiche findSquadStats(String id) throws JSONException {
		Statistiche squadstats = null;
		// TODO Auto-generated method stub
		try {
			URL url = new URL("https://fortnite-api.theapinetwork.com/prod09/users/public/br_stats?user_id=" + id + "&platform=pc");
			connection = (HttpURLConnection) url.openConnection();
		
			connection.setRequestMethod("GET");
			
			//request header
			connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			connection.addRequestProperty("Authorization", key);
			
			connection.connect();
			int status = connection.getResponseCode();
			
			System.out.println(status);			
		
			if(status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);					
				}
				reader.close();
			}else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);					
				}
				reader.close();
			}
			System.out.println(responseContent.toString());
				
			    JSONObject jsonObject = new JSONObject(responseContent.toString());
			    JSONObject total = (JSONObject) jsonObject.get("stats");
			    
			    Integer matches = total.getInt("matchesplayed_squad");
			    Integer win = total.getInt("placetop1_squad");
			    Integer kill = total.getInt("kills_squad");
			    Integer top10 = total.getInt("placetop3_squad");
			    Integer top25 = total.getInt("placetop6_squad");
			    Double score = total.getDouble("score_squad");
			    Double kd = total.getDouble("kd_squad");
			    Double winrate = total.getDouble("winrate_squad");
			    
			    squadstats = new Statistiche(matches,kill,win,score,top25,top10,"squad",kd,winrate);	    

		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			connection.disconnect();
		}
		return squadstats;
	}

}
