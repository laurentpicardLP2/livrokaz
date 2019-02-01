package co.jlv.livrokaz.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.jlv.livrokaz.services.IAuthenticationFacade;

@Controller
@RestController
@RequestMapping("/livrokaz")
public class SecurityController {
    @Autowired
    private IAuthenticationFacade authenticationFacade;
 
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> currentUserNameSimple( HttpServletResponse httpResponse) {
        Authentication authentication = authenticationFacade.getAuthentication();
        System.out.println(authentication.getName());
        try {
        	httpResponse.sendRedirect("/");
            return null;
        } catch(Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }
}