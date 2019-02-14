package co.jlv.livrokaz.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

   @Override
   public boolean enableHttpSessionEventPublisher() {
	   System.out.println("********enableHttpSessionEventPublisher************");
     return true;
 }

}