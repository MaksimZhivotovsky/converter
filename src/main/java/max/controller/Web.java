package max.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import max.model.Currency;
import max.repository.CurrencyRepo;
import max.repository.HistoryRepo;
import max.util.CurrenciesScraper;
import max.util.DateUtils;

@Controller
@RequestMapping("/app")
public class Web {

	private final CurrencyRepo cr;
	private final HistoryRepo hr;

	public Web(CurrencyRepo cr, HistoryRepo hr) {
		this.cr = cr;
		this.hr = hr;
	}

	@GetMapping
	private String app(Model model) throws ParserConfigurationException, SAXException, ParseException, IOException {

		if (!cr.existsByDate(DateUtils.getCurrentDateWOTime())) {
			cr.saveAll(new CurrenciesScraper().scrap(DateUtils.getCurrentDateWOTime()));
			cr.save(Currency.getBase());
		}

		model.addAttribute("currencies", cr.findAll());
		return "index";
	}

	@GetMapping("/today")
	private String today(Model model) throws ParserConfigurationException, SAXException, ParseException, IOException {

		if (!cr.existsByDate(DateUtils.getCurrentDateWOTime())) {
			cr.saveAll(new CurrenciesScraper().scrap(DateUtils.getCurrentDateWOTime()));
			cr.save(Currency.getBase());
		}

		model.addAttribute("currencies", cr.findAllByDate(DateUtils.getCurrentDateWOTime()));

		return "today";
	}

	@GetMapping("/history")
	private String history(Model model){
		Date current = DateUtils.getCurrentDateWOTime();

		model
			.addAttribute("from", hr.getFromDistinct().stream().sorted().collect(Collectors.toList()))
			.addAttribute("to", hr.getToDistinct().stream().sorted().collect(Collectors.toList()))
			.addAttribute("history", hr.getConvertHistories(current, 100));

		return "history";
	}

}
