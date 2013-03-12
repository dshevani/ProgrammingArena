package cityguides;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class CityGuideData {

	String description;
	String name;
	String breadcrumb;

	JSONObject weather;
	List<String> airportCodes = new ArrayList<String>();
	JSONArray recommendations;
	
	public CityGuideData(String name) {
		super();
		this.name = name;
	}
	public CityGuideData() {
		super();
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public JSONObject getWeather() {
		return weather;
	}
	public void setWeather(JSONObject weather) {
		this.weather = weather;
	}
	public List<String> getAirportCodes() {
		return airportCodes;
	}
	public void addAirportCode(String airportCode) {
		this.airportCodes.add(airportCode);
	}
	public JSONArray getRecommendations() {
		return recommendations;
	}
	public void setRecommendations(JSONArray recommendations) {
		this.recommendations = recommendations;
	}	
	public String getBreadcrumb() {
		return breadcrumb;
	}
	public void setBreadcrumb(String breadcrumb) {
		this.breadcrumb = breadcrumb;
	}
}
