package de.ba.bund.burnafterreading.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewController {
    @GetMapping("/view/{id}")
    public String forwardToIndex(@PathVariable String id) {
        return "forward:/index.html";
    }
}

