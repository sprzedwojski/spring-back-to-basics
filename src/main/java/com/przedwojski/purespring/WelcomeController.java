package com.przedwojski.purespring;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class WelcomeController {

//    private final Logger logger = LoggerFactory.getLogger(WelcomeController.class);
//    private final HelloWorldService helloWorldService;
    private final FootballClub manchesterUnited; // matches by bean id

    @Autowired
    public WelcomeController(FootballClub manchesterUnited) {
        this.manchesterUnited = manchesterUnited;
    }
//    @Autowired
//    public WelcomeController(HelloWorldService helloWorldService) {
//        this.helloWorldService = helloWorldService;
//    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String index(Map<String, Object> model) {

//        logger.debug("index() is executed!");
//
//        model.put("title", helloWorldService.getTitle(""));
//        model.put("msg", helloWorldService.getDesc());

//        return "Hello, Spring!@";
        return manchesterUnited.getName();
    }

//    @RequestMapping(value = "/hello/{name:.+}", method = RequestMethod.GET)
//    public ModelAndView hello(@PathVariable("name") String name) {
//
//        logger.debug("hello() is executed - $name {}", name);
//
//        ModelAndView model = new ModelAndView();
//        model.setViewName("index");
//
//        model.addObject("title", helloWorldService.getTitle(name));
//        model.addObject("msg", helloWorldService.getDesc());
//
//        return model;
//
//    }

}