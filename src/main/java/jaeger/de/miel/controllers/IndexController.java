package jaeger.de.miel.controllers;

import jaeger.de.miel.model.Hello;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
public class IndexController {

    @RequestMapping(value = "/hello",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Hello hello(@RequestParam(name = "name", defaultValue = "world") String name) {
        Hello h = new Hello();
        if (name.isEmpty()) h.setGreeting("Hello world");
        else h.setGreeting("Hello " + name);
        return h;
    }

    @RequestMapping(value = "/ping",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody String ping() {
        return "pong";
    }

    @RequestMapping(value = "/",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String index(Model model) {
        return "index";
    }

}
