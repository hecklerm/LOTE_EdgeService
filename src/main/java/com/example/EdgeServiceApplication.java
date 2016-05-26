package com.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableZuulProxy
//@EnableBinding(Source.class)
public class EdgeServiceApplication {
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

	public static void main(String[] args) {
		SpringApplication.run(EdgeServiceApplication.class, args);
	}
}

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

class Quote {
    private Long id;
    private String text;
    private String source;

    public Quote() {
    }

    public Quote(Long id, String text, String source) {
        this.id = id;
        this.text = text;
        this.source = source;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}