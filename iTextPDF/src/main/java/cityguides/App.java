package cityguides;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import helper.*;

public class App 
{
	/*
	 * Generates PDF for the city identified by id
	 * This function is called only when PDF is to be created/updated
	 * 
	 */
	
	public static final String RESOURCE = "resources/posters/%s.jpg";
	
	public static void generatePDF (String id)
			throws DocumentException, IOException {
		
		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
		String FILE = System.getProperty("user.home") + "/pdf/" + id + ".pdf";
		
		// Step 1 : Create Document instance with A4 size and color
		Rectangle pagesize = new Rectangle(595f, 842f);
		pagesize.setBackgroundColor(CityGuideColors.PAGE_COLOR);
		Document document = new Document(pagesize);
		
		// Step 2 : Create DocWriter instance using PdfWriter
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
		writer.setPdfVersion(PdfWriter.VERSION_1_6);
		writer.setCompressionLevel(0);
		writer.setStrictImageSequence(true);
		
		// Step 3 : Add settings applicable for all pages
		addMetaData(document);
		
		// Step 4 : Open the document
		document.open();
		
		// Step 5 : Add content to the document
		addTravelHeader(document, writer);
		addDataForCity(id, document, writer);
		//addRandomContent(document, writer);
		
		// Step 6 : Close the document
		document.close();

	}
	
	private static void addMetaData(Document document) {
		document.setMargins(30, 30, 50, 50);
		document.addTitle("Yahoo Travel City Guide");
		document.addKeywords("Yahoo, Travel, travel.yahoo.com, yahootravel");
		document.addCreator("yahootravel");
		document.addHeader("Expires", "0");
	}
	
	private static void addTravelHeader(Document document, PdfWriter writer) 
			throws IOException, DocumentException {

        PdfContentByte canvas = writer.getDirectContent();
        canvas.setRGBColorFill(0x00, 0x00, 0x10);
        
        canvas.rectangle(0, 807, 595, 35);
        canvas.fill();
        
        canvas.rectangle(0, 0, 595, 35);
        canvas.fill();
        
        Image img = Image.getInstance("src/main/resources/travel-us-big.gif");
        img.setAbsolutePosition(10, 810);
        img.scaleAbsoluteWidth(170);
        img.scaleAbsoluteHeight(25);
        canvas.addImage(img);
        
        Chunk slogan = new Chunk(CityGuideConstants.SLOGAN, CityGuideFonts.SMALLWHITE);
        Phrase phrase = new Phrase();
        phrase.add(slogan);
        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase, 240, 815, 0);

        //document.setMargins(20, 20, 50, 20);
	}
	
	private static JSONObject addDataForCity(String id, Document document, PdfWriter writer) 
			throws IOException, DocumentException {
		JSONObject json_response = null;
		//System.out.println(String.format(CityGuideConstants.API_URL, id));
		try {
			String response = HTTPUtils.get(String.format(CityGuideConstants.API_URL, id));
			CityGuideMainImage image = null;
			CityGuideData data = null;
			if (!StringUtils.isEmpty(response)) {
				json_response = new JSONObject(response);
				data = new CityGuideData(json_response.getString("Name"));
				
				// Extracting Image Details
				if (json_response.get("LeadPhoto") != null) {
					image = new CityGuideMainImage(
							json_response.getString("LeadPhoto"),
							json_response.getString("LeadPhotoOwnerName"),
							json_response.getString("LeadPhotoOwnerUrl"),
							json_response.getInt("LeadPhotoWidth"),
							json_response.getInt("LeadPhotoHeight")
							);
				}
				
				// Extracting Description Details
				StringBuffer desc_sb = new StringBuffer();
				if (json_response.has("FullIntroInfo")) {
					JSONObject description = json_response.getJSONObject("FullIntroInfo");
					desc_sb.append(StringEscapeUtils.unescapeHtml(description.get("Intro").toString()));
				}
				data.setDescription(desc_sb.toString());
				
				// Extracting Weather Data
				if (json_response.has("Weather")) {
					data.setWeather(json_response.getJSONObject("Weather"));
				}
				
				// Extracting AirportCodes
				if (json_response.has("AirportCodes")) {
					JSONArray codes = json_response.getJSONArray("AirportCodes");
					for (int i=0; i<codes.length(); i++) {
						data.addAirportCode(codes.getString(i));
					}
				}
				
				// Extracting Recommendations
				if (json_response.has("Recommendations") 
						&& json_response.get("Recommendations") instanceof JSONArray) {
					data.setRecommendations(json_response.getJSONArray("Recommendations"));
				}
				
				// Generating Valid XHTML from data
				String validHTML = generateValidHTML(image, data);
				//System.out.println(validHTML);
				XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(validHTML));
				decoratePage(image,data,document,writer);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("IOException occured");
		}
		return json_response;
	}
	
	private static void decoratePage(CityGuideMainImage image,
			CityGuideData data, Document document, PdfWriter writer) 
			throws IOException, DocumentException {
		
		PdfContentByte canvas = writer.getDirectContent();
		Image img = Image.getInstance("src/main/resources/black_gradient.png");
		img.setAbsolutePosition(405, 511);
		img.scaleAbsoluteWidth(160);
		img.scaleAbsoluteHeight(281);
		img.setTransparency(new int[]{ 0xF0, 0xFF });
        canvas.addImage(img);
        
        Chunk cityname = new Chunk(data.getName(), CityGuideFonts.BIGWHITE);
        Paragraph paragraph = new Paragraph();
        paragraph.setLeading(12);
        paragraph.add(cityname);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, paragraph, 410, 540, 0);
		
	}

	private static String generateValidHTML(CityGuideMainImage image, CityGuideData data) {
		StringBuffer corrected = new StringBuffer();
		corrected.append("<html xml:space=\"preserve\" xmlns=\"http://www.w3.org/1999/xhtml\">");
		corrected.append("<head><style>h1 {bottom:0; color:white; display:inline-block; vertical-align: top; text-align:center; position:absolute;}</style></head>");
		corrected.append("<body>");
		if (image != null) {
			corrected.append("<div style=\"width:710px\"><div style=\"width:500px; float:left\"><img height=\"375px\" width=\"500px\" src=\"" + image.getImageURL() 
					+ "\"></img></div>" 
					//+ "<div style=\"width:210px; height:375px; float:left; background-color:black\"><h1>" 
					//+ data.getName() +"</h1></div>"
					+ "</div>");
		} else {
			
		}
		if (data.getDescription() != null) {
			corrected.append("<div align=\"justify\"><h4>Description</h4>");
			Pattern p = Pattern.compile("<p>(.*?)</p>", Pattern.MULTILINE);
			Matcher m = p.matcher(data.getDescription());
			boolean flag_pExists = false; 
			while(m.find()){
				flag_pExists = true;
				corrected.append("<p align=\"justify\">");
				corrected.append(m.group(1).replaceAll("&", "& \u00a0"));
				corrected.append("</p>");
			}
			if (flag_pExists == false) {
				corrected.append("<p align=\"justify\">");
				corrected.append(data.getDescription().replaceAll("&", "& \u00a0"));
				corrected.append("</p>");
			}
			corrected.append("</div>");
		}
		corrected.append("</body></html>");
		return corrected.toString();
	}
	
	public static void addRandomContent(Document document, PdfWriter writer) 
			throws IOException, DocumentException {
		document.newPage();
		//XMLWorkerHelper.getInstance().parseXHtml(writer, document, 
		//        new FileInputStream("/Users/shevanid/Documents/workspace-yahoo/iTextPDF/src/main/resources/lowargie.html"));
	}
	
	public static void main(String[] args) {
		try {
			//generatePDF("191501822");
			//generatePDF("191501629");
			//generatePDF("191501793");
			generatePDF("191501853");
			
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error while creating the PDF");
		}
	}
}
