package com.google.cloud.paflynn.samples.recaptcha.guestbook.restservice;

import com.google.cloud.paflynn.samples.recaptcha.guestbook.resouces.Greeting;
import com.google.cloud.paflynn.samples.recaptcha.guestbook.resouces.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class GreetingController {

    private final GreetingRepository greetingRepository;


    @Autowired
    public GreetingController(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    @GetMapping("/greetings")
    public Page<Greeting> listGreetings(Pageable page) {
        return greetingRepository.findAll(page);
    }

    @RequestMapping(value = "/greetings", method = RequestMethod.POST, consumes = "application/json")
    public Greeting greeting(@RequestBody String postPayload) {
        JacksonJsonParser parser = new JacksonJsonParser();
        Map<String, Object> values = parser.parseMap(postPayload);
        greetingRepository.save(new Greeting((String) values.get("username"), (String) values.get("message")));
        return null;
    }
}