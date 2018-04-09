package com.ikeeps.pippy.statistics.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ikeeps.pippy.statistics.domain.Currency;
import com.ikeeps.pippy.statistics.domain.ExchangeRatesContainer;

@FeignClient(name="rates-client", url="${rates.url}", fallback=ExchangeRatesFallback.class)
public interface ExchangeRatesClient {

	@RequestMapping(method = RequestMethod.GET, value = "/latest")
	ExchangeRatesContainer getRates(@RequestParam("base") Currency base);

}
