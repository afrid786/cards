/**
 * 
 */
package com.afrid.cards.controller;

import com.afrid.cards.config.CardsConfig;
import com.afrid.cards.model.Cards;
import com.afrid.cards.model.Customer;
import com.afrid.cards.model.Properties;
import com.afrid.cards.repository.CardsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class CardsController {

	@Autowired
	private CardsRepository cardsRepository;
	
	@Autowired
	private CardsConfig cardsConfig;

	@PostMapping("/cards")
	public List<Cards> getCardDetails(@RequestBody Customer customer) {
		List<Cards> cards = cardsRepository.findByCustomerId(customer.getCustomerId());
		return cards;

	}

	@GetMapping("/application/properties")
	public String getProperties() {
		String propertiesString = "";
		try {
			ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

			Properties properties = new Properties(cardsConfig.getMsg(), cardsConfig.getBuildVersion(),
					cardsConfig.getMailDetails(), cardsConfig.getActiveBranches());

			propertiesString = objectWriter.writeValueAsString(properties);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return propertiesString;
	}

}
