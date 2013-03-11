package helper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.lang.StringEscapeUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.SimpleXmlSerializer;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XmlSerializer;
import org.w3c.dom.Document;

public class HTMLCleaner {

	public final static HtmlCleaner cleaner;
	public final static XmlSerializer serializer;

	static {
		CleanerProperties props = new CleanerProperties();
		props.setUseEmptyElementTags(false);
		props.setOmitXmlDeclaration(false);
		props.setAllowMultiWordAttributes(true);
		props.setAllowHtmlInsideAttributes(true);
		props.setAdvancedXmlEscape(false);
		props.setTranslateSpecialEntities(false);
		props.setNamespacesAware(false);
		props.setRecognizeUnicodeChars(false);
		cleaner = new HtmlCleaner(props);
		serializer = new SimpleXmlSerializer(props);
	}

	/**
	 * Cleans the given HTML content
	 * @param html - Raw HTML
	 * 
	 * @return - Cleaned HTML
	 * 
	 * @throws IOException
	 */
	public static String clean(String html) throws IOException {
		TagNode node = cleaner.clean(html);
		return serializer.getAsString(node);
	}

	/**
	 * Cleans (Tidy) the given HTML Content and returns the text content from the html.
	 *  
	 * @param html - HTML to be processed.
	 * 
	 * @return - text content from the html
	 */
	public static String getText(String html) {
		String text = html;

		try {
			String xml = clean(html);

			if (xml != null) {
				Document d = XMLUtil.parseXML(new ByteArrayInputStream(xml.getBytes()));

				if (d != null) {
					String htmlText = XMLUtil.getText(d.getFirstChild());

					if (htmlText != null) {
						text = StringEscapeUtils.unescapeHtml(htmlText);
					}
				}
			}
		}
		catch (Exception e) {
			LogUtils.error(HTMLCleaner.class, "HTMLCleaner: exception occurred while getting text from html: ", e);
		}

		return text;
	}

	public static void main(String[] args) {
		System.out.println("A:" + getText("<html>Hello, <a>There!</a></html>"));
	}
}
