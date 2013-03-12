package cityguides;

import helper.HTTPUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;


public class testTable {

	public static final String RESOURCE = "http://farm1.hv-static.flickr.com/90/244311748_6003bed15b.jpg";

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
		addDataForCity(id, document, writer);
		//addRandomContent(document, writer);

		// Step 6 : Close the document
		document.close();

	}

	public static void main(String[] args) {
		try {
			generatePDF("191501889");
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error while creating the PDF");
		}
	}

	private static void addMetaData(Document document) {
		document.setMargins(30, 30, 50, 50);
		document.addTitle("Yahoo Travel City Guide");
		document.addKeywords("Yahoo, Travel, travel.yahoo.com, yahootravel");
		document.addCreator("yahootravel");
		document.addHeader("Expires", "0");
	}


	private static void addDataForCity(String id, Document document, PdfWriter writer) 
			throws IOException, DocumentException {

		JSONObject json_response = null;
		CityGuideMainImage image = null;
		CityGuideData data = null;

		try {

			String response = HTTPUtils.get(String.format(CityGuideConstants.API_URL, id));

			if (StringUtils.isEmpty(response)) {
				// Do something in case of error
			}

			json_response = new JSONObject(response);
			data = new CityGuideData(json_response.getString("Name"));
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

			// Extracting Breadcrumb string
			if (json_response.has("Breadcrumb")
					&& json_response.get("Breadcrumb") instanceof JSONArray) {
				JSONArray crumbs_array = json_response.getJSONArray("Breadcrumb");
				JSONObject crumb;
				StringBuffer crumb_string = new StringBuffer();
				for (int i=1; i<crumbs_array.length(); i++) {
					crumb = crumbs_array.getJSONObject(i);
					crumb_string.append(crumb.get("Name"));
					crumb_string.append(" > ");
				}
				crumb_string.append(json_response.getString("Name"));
				data.setBreadcrumb(crumb_string.toString());
				System.out.println(crumb_string.toString());
			}

			// Generating Valid XHTML from data
			addTravelHeader(document, writer, image, data);
			String validHTML = generateValidHTML(image, data);
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(validHTML));

		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("IOException occured");
		}
	}

	private static void addTravelHeader(Document document, 
			PdfWriter writer, CityGuideMainImage image, CityGuideData data) 
					throws IOException, DocumentException {

		PdfContentByte canvas = writer.getDirectContent();
		
		canvas.setRGBColorFill(0x40, 0x40, 0x40);
		canvas.rectangle(0, 807, 595, 35);
		canvas.fill();

		Image img = Image.getInstance("src/main/resources/travel-us-big.gif");
		img.setAbsolutePosition(10, 810);
		img.scaleAbsoluteWidth(170);
		img.scaleAbsoluteHeight(25);
		canvas.addImage(img);

		//Chunk slogan = new Chunk(CityGuideConstants.SLOGAN, CityGuideFonts.SMALLWHITE);
		//Phrase phrase = new Phrase();
		//phrase.add(slogan);
		//ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, phrase, 540, 815, 0);

		img = Image.getInstance(image.getImageURL());
		img.setBorderColor(BaseColor.WHITE);
		img.scaleToFit(1000, 216);
		img.setAbsolutePosition(27, 582);
		canvas.addImage(img);

		canvas.setRGBColorFill(0x40, 0x40, 0x40);
		canvas.roundRectangle(25, 580, 545, 220, 6);
		canvas.setLineWidth(3);
		canvas.stroke();

		int start_position = 600 - (int)img.getScaledWidth() + 20;	
		Paragraph para_welcome = new Paragraph(12, new Chunk("Welcome User !! You downloaded following page :", CityGuideFonts.SMALLBLACK));
		Paragraph para_crumb = new Paragraph(12, new Chunk(data.getBreadcrumb(), CityGuideFonts.SMALLBLACK));
		Paragraph para_date = new Paragraph(12, new Chunk("on " + (new Date()).toString(), CityGuideFonts.SMALLBLACK));
		Paragraph para_name = new Paragraph(12, new Chunk(data.getName(), CityGuideFonts.BIGBLACK));
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, para_welcome, start_position, 785, 0);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, para_crumb, start_position, 773, 0);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, para_date, start_position, 761, 0);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, para_name, start_position + (int)((200-10*data.getName().length())/2), 600, 0);
		
		System.out.println("Image Width : " + img.getScaledWidth() 
				+ " Image Height : " +  img.getScaledHeight());
	}


	private static String generateValidHTML(CityGuideMainImage image, CityGuideData data) {
		StringBuffer corrected = new StringBuffer();
		corrected.append("<html xml:space=\"preserve\" xmlns=\"http://www.w3.org/1999/xhtml\">");
		corrected.append("<head><style>h1 {bottom:0; color:white; display:inline-block; vertical-align: top; text-align:center; position:absolute;}</style></head>");
		corrected.append("<body>");
		corrected.append("<div height=\"275px\")></div>");
		if (image != null) {

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
				corrected.append("<p align=\"justify\" font-size:8pt>");
				String description = data.getDescription().replaceAll("<.*?>", "");
				corrected.append(description.replaceAll("&", "& \u00a0"));
				corrected.append("</p>");
			}
			corrected.append("</div>");
		}
		corrected.append("</body></html>");
		return corrected.toString();
	}

}
