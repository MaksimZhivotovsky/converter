package max.util;

import java.math.BigDecimal;

import max.model.Currency;

public class CurrencyConverter {

	private static final String BASE = "RUB";

	/**
	 * Converting one currency to other. Use Russian rubble as base
	 * @param sum  : Currency
	 * @param from : Currency
	 * @param to   : Currency
	 * @return converted value
	 */
	public static BigDecimal convert(BigDecimal sum, Currency from, Currency to) {

		if (sum == null || from == null || to == null){
			throw new RuntimeException("Произошла ошибка во время конвертации");
		}

		BigDecimal result = sum;

		if(from.equals(to)){
			return sum;
		}
		if(!from.getCharCode().contains(BASE))
			result = sum.multiply(from.getValue().divide(from.getNominal() , 5, 5 ) );

		if(!to.getCharCode().contains(BASE))
			result = result.divide(to.getValue().divide(to.getNominal()), 5, 5);

		return result;
	}
}
