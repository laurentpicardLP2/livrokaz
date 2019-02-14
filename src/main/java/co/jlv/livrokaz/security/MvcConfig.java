package co.jlv.livrokaz.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
    	System.out.println("MvcConfig");
        //registry.addViewController("/web/gestionbooks").setViewName("/web/gestionbooks.html");
//        registry.addViewController("/").setViewName("index.html");
//        registry.addViewController("/signup").setViewName("signup.html");
        registry.addViewController("/login").setViewName("login.html");
        registry.addViewController("/error").setViewName("home.html");
    }

}
