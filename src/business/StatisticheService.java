package business;

import java.util.List;

import org.json.JSONException;

import domain.Statistiche;

public interface StatisticheService {
	
	Statistiche findOverallStats(String id)throws JSONException;
	Statistiche findSoloStats(String id)throws JSONException;
	Statistiche findDuoStats(String id)throws JSONException;
	Statistiche findSquadStats(String id)throws JSONException;

}
