package business.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jmx.snmp.Timestamp;

import business.NotizieService;
import domain.Notizia;


public class NotizieServiceImpl implements NotizieService {
	
	private String key="51ec729be5d23f5051b16e5baef90339";
	private static HttpURLConnection connection;
	BufferedReader reader;
	String line;
	StringBuffer responseContent = new StringBuffer();
    List<Notizia> list = new ArrayList<Notizia>();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
    URL url = null;
	
	public List<Notizia> findallNotizie(String language) throws JSONException {
		
		if(language.equals("it")) {
			 try {
				url = new URL("https://fortnite-api.theapinetwork.com/br_motd/get?language=it");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			 try {
				url = new URL("https://fortnite-api.theapinetwork.com/br_motd/get?language=en");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		try {
			
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
			    JSONArray tsmresponse = (JSONArray) jsonObject.get("data");

			    for(int i=0; i<tsmresponse.length(); i++)
			    {
			    	String tit=tsmresponse.getJSONObject(i).getString("title").replaceAll("Ã¨", "è").replaceAll("Ã©", "è").replaceAll("Ãˆ", "E'").replaceAll("Ã²", "ò").replaceAll("Ã", "à");
			    	String desc=tsmresponse.getJSONObject(i).getString("body").replaceAll("Ã¨", "è").replaceAll("Ã©", "è").replaceAll("Ãˆ", "E'").replaceAll("Ã²", "ò").replaceAll("Ã", "à");
			    	String cop=tsmresponse.getJSONObject(i).getString("image");
			    	Long tempdata = tsmresponse.getJSONObject(i).getLong("time");
			    		
			    	Date d = new Date(tempdata*1000);

			    	String data= formatter.format(d);
			 
			        list.add(new Notizia(tit,desc,cop,data));
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
