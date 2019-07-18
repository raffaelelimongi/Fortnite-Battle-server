package business.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import business.ArmiService;
import domain.Arma;
import domain.Notizia;

public class ArmiServiceImpl implements ArmiService {
	
	private String key="51ec729be5d23f5051b16e5baef90339";
	private static HttpURLConnection connection;
	BufferedReader reader;
	String line;
	StringBuffer responseContent = new StringBuffer();
    List<Arma> list = new ArrayList<Arma>();

	@Override
	public List<Arma> findAllWeapons() throws JSONException {
		
		try {
			URL url = new URL("https://fortnite-api.theapinetwork.com/weapons/get");
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
			    JSONObject data = (JSONObject) jsonObject.get("data");
			    JSONArray item = (JSONArray) data.get("entries");

			    for(int i=0; i<item.length(); i++)
			    {
			    	String name=item.getJSONObject(i).getString("name");
			    	String rarity=item.getJSONObject(i).getString("rarity");
			    	String image=item.getJSONObject(i).getString("image");
			    	
			    	JSONObject statw = (JSONObject) item.getJSONObject(i).get("stats");
			    	
			    	Double hitbody = statw.getDouble("hit_body");
			    	Double hithead = statw.getDouble("hit_head");
			    	Double firerate = statw.getDouble("firerate");
			    	Integer caricatore = statw.getInt("magazinesize");
			    	Double reload = statw.getDouble("reloadtime");
			    	
			    	list.add(new Arma(name,rarity,image,hitbody,hithead,firerate,reload,caricatore));
			    	
			    }


		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			connection.disconnect();
		}
		return list;
	}

}
