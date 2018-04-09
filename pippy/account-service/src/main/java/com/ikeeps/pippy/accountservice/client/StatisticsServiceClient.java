package com.ikeeps.pippy.accountservice.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ikeeps.pippy.accountservice.domain.Account;

@FeignClient(name = "statistics")
public interface StatisticsServiceClient {

	@RequestMapping(method = RequestMethod.PUT, value = "/{accountName}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	void updateStatistics(@PathVariable("accountName") String accountName, Account account);

}
