package max.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import max.model.Currency;

public class CurrenciesScraper {

	private final String CBR_URL = "http://www.cbr.ru/scripts/XML_daily.asp";
	private final String DATE_PARAM = "?date_req=";

	public List<Currency> scrap(Date forDate) throws IOException, ParserConfigurationException, SAXException, ParseException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(CBR_URL + DATE_PARAM + DateUtils.cbrFormat(forDate));
		document.getDocumentElement().normalize();

		Element root = document.getDocumentElement();
		Date date = new SimpleDateFormat("dd.MM.yyyy").parse(root.getAttribute("Date"));
		NodeList nList = document.getElementsByTagName("Valute");
		List<Currency> currList = new ArrayList<>();

		for (int temp = 0; temp < nList.getLength(); temp++){

			Node node = nList.item(temp);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				Currency currency = new Currency();

				Element e = (Element) node;

				currency.setIdent(e.getAttribute("ID"));
				currency.setNumCode(new BigDecimal(e.getElementsByTagName("NumCode").item(0).getTextContent()));
				currency.setCharCode(e.getElementsByTagName("CharCode").item(0).getTextContent());
				currency.setNominal(new BigDecimal(e.getElementsByTagName("Nominal").item(0).getTextContent()));
				currency.setName(e.getElementsByTagName("Name").item(0).getTextContent());
				currency.setValue(new BigDecimal(e.getElementsByTagName("Value").item(0).getTextContent().replace(',', '.')));
				currency.setDate(date);

				currList.add(currency);
			}
		}
		return currList;
	}


}
