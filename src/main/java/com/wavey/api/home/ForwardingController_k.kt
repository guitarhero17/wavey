package com.wavey.api.home

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping

/**
 * This controller ensures that all requests are going to be
 * redirected back to "/" so they can be handled by Routify
 * and not Spring.
 *
 *
 * [][<a href=]//stackoverflow.com/questions/38783773/spring-boot-single-page-application-forward-every-request-to-index-html">Forward every request to index html">&lt;a href=&quot;https://stackoverflow.com/questions/38783773/spring-boot-single-page-application-forward-every-request-to-index-html&quot;&gt;Forward every request to index html&lt;/a&gt;
 *
 */
@Controller
@CrossOrigin
class ForwardingController_k {
    @RequestMapping("/{path:[^\\.]+}")
    fun forward(): String {
        return "forward:/"
    }
}
