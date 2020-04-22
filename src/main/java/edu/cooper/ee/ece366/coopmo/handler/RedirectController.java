package edu.cooper.ee.ece366.coopmo.handler;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RequestMapping(path = "/redirect", method = RequestMethod.GET)
public class RedirectController {
    public String handleGet(HttpServletResponse response) {
        response.setHeader("Location", "localhost:3000");
        response.setStatus(302);
        return "redirect:http://localhost:3000";
    }
}