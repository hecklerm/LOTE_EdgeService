package com.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by markheckler on 6/16/16.
 */
@RestController
@RefreshScope
class QuoteController {
//    @Autowired
//    Source source;

    @Autowired
    RestTemplate restTemplate;

    @Value("${quote}")
    private String defaultQuote;

    @RequestMapping("/quotorama")
    @HystrixCommand(fallbackMethod = "getDefaultQuote")
    public String getRandomQuote() {
        return restTemplate.getForObject("http://quote-service/random", String.class);
    }

    public String getDefaultQuote() {
        return defaultQuote;
    }

//    @RequestMapping(value = "newquote", method = RequestMethod.POST)
//    public void addNewQuote(@RequestBody Quote quote) {
//        this.source.output().send(MessageBuilder.withPayload(quote).build());
//    }
}