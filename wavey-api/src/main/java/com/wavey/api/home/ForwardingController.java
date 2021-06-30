package com.wavey.api.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller ensures that all requests are going to be
 * redirected back to "/" so they can be handled by Routify
 * and not Spring.
 * 
 * <p>{@link https://stackoverflow.com/questions/38783773/spring-boot-single-page-application-forward-every-request-to-index-html}</p>
 *
 */
@Controller
@CrossOrigin
public class ForwardingController {
	
    @RequestMapping("/{path:[^\\.]+}")
    public String forward() {
        return "forward:/";
    }
    
//    @RequestMapping(value = "/{[path:[^\\.]*}")
//    public void redirect(HttpServletResponse response) throws IOException {
//        response.sendRedirect("/");
//    }
}
