package helper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;

public class HTMLParser {

	public void printHTML(String url) {
		try {
			Document doc = Jsoup.connect(url).get();
			System.out.println(doc.outerHtml());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		HTMLParser htmlparser = new HTMLParser();
		htmlparser.printHTML("http://travel.yahoo.com/p-travelguide-487820-funchal_portugal_vacations-i");
	}
}
