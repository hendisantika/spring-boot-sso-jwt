package com.hendisantika.sso.jwt.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * Project : sso-jwt
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 07/10/17
 * Time: 22.37
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class ResourceController {
    private static final String jwtTokenCookieName = "JWT-TOKEN";

    @RequestMapping("/")
    public String home() {
        return "redirect:/protected-resource";
    }

    @RequestMapping("/protected-resource")
    public String protectedResource() {
        return "protected-resource";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletResponse httpServletResponse) {
        CookieUtil.clear(httpServletResponse, jwtTokenCookieName);
        return "redirect:/";
    }
}
