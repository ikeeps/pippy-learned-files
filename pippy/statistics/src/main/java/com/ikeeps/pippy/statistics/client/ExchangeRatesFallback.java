package com.ikeeps.pippy.statistics.client;

import com.ikeeps.pippy.statistics.domain.Currency;
import com.ikeeps.pippy.statistics.domain.ExchangeRatesContainer;

public class ExchangeRatesFallback implements ExchangeRatesClient {

	@Override
	public ExchangeRatesContainer getRates(Currency base) {
		ExchangeRatesContainer container = new ExchangeRatesContainer();
		container.setBase(Currency.getBase());
		return container;
	}

}
