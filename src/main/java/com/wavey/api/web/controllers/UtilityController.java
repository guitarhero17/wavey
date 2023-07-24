package com.wavey.api.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@CrossOrigin
public class UtilityController {

    /**
     * This controller ensures that all requests are going to be
     * redirected back to "/" so they can be handled by Routify
     * and not Spring.
     *
     * <p>{@link <a href="https://stackoverflow.com/questions/38783773/spring-boot-single-page-application-forward-every-request-to-index-html">Forward every request to index html</a>}</p>
     *
     */
    @RequestMapping("/{path:[^.]+}")
    public String forwardToHome() {
        return "forward:/";
    }

    /**
     * A simple utility controller to check the validity of the passed
     * basic auth credentials. The endpoint can only be accessed by
     * authenticated requests. If the credentials are incorrect,
     * a 401 http status will be sent to the client.
     *
     */
    @GetMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, String>> login() {
        Map<String, String> response = new HashMap<>();
        response.put("success", "Valid credentials");
       return ResponseEntity.ok(response);
    }
}
