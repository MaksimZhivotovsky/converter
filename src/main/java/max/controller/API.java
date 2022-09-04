package max.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import max.model.ConvertHistory;
import max.model.Currency;
import max.repository.CurrencyRepo;
import max.repository.HistoryRepo;
import max.util.CurrenciesScraper;
import max.util.CurrencyConverter;
import max.util.DateUtils;

@RestController
@RequestMapping("/api")
public class API {

	private final CurrencyRepo cr;
	private final HistoryRepo hr;
	
	public API(CurrencyRepo cr, HistoryRepo hr) {
		this.cr = cr;
		this.hr = hr;
	}

	@GetMapping("/convert")
	private BigDecimal convert(
			@RequestParam String from,
			@RequestParam String to,
			@RequestParam String sum
	) throws ParserConfigurationException, SAXException, ParseException, IOException {

		if (!cr.existsByDate(DateUtils.getCurrentDateWOTime())) {
			cr.saveAll(new CurrenciesScraper().scrap(DateUtils.getCurrentDateWOTime()));
			cr.save(Currency.getBase());
		}

		Currency curFrom = cr.getFirstByCharCodeOrderByDateDesc(from).get();
		Currency curTo = cr.getFirstByCharCodeOrderByDateDesc(to).get();

		BigDecimal sumFrom = new BigDecimal(sum);

		BigDecimal sumTo = CurrencyConverter.convert(sumFrom, curFrom, curTo);

		ConvertHistory ch = new ConvertHistory();
		ch.setFrom(curFrom.getCharCode() + " (" + curFrom.getName() + ")");
		ch.setTo(curTo.getCharCode() + " (" + curTo.getName() + ")");
		ch.setValue_from(sumFrom);
		ch.setValue_to(sumTo);
		ch.setDate(DateUtils.getCurrentDateWOTime());

		hr.save(ch);

		return sumTo;
	}

	@GetMapping("/history")
	private List<ConvertHistory> history_post(
			@RequestParam String date,
			@RequestParam String from,
			@RequestParam String to
	) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date parsed_date = dateFormat.parse(date);

		return hr.findAllByDateAndFromAndToOrderById(parsed_date, from, to);

	}

	@GetMapping("/drop-db")
	private void app(){
		cr.deleteAll();
		hr.deleteAll();
	}

}
