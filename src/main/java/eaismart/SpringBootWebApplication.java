package eaismart;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Iekiny Marcel Feb 12, 2020
 */
@SpringBootApplication
public class SpringBootWebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
       SpringApplication.run(SpringBootWebApplication.class, args);
    	//NumberFormat nf = new DecimalFormat("#,###,###,###"); 
    	//System.out.println(nf.format(100000000000L));
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootWebApplication.class);
    }
    /* 
	 @Bean
	 public ServletWebServerFactory servletContainer() {
	        // Enable SSL Trafic
	        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
	            @Override
	            protected void postProcessContext(Context context) {
	                SecurityConstraint securityConstraint = new SecurityConstraint();
	                securityConstraint.setUserConstraint("CONFIDENTIAL");
	                SecurityCollection collection = new SecurityCollection();
	                collection.addPattern("/*");
	                securityConstraint.addCollection(collection);
	                context.addConstraint(securityConstraint);
	            }
	        };

	        // Add HTTP to HTTPS redirect
	        tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());

	        return tomcat;
	    }
     */
 /*
	    We need to redirect from HTTP to HTTPS. Without SSL, this application used
	    port 8082. With SSL it will use port 8443. So, any request for 8082 needs to be
	    redirected to HTTPS on 8443.
     */
 /*  private Connector httpToHttpsRedirectConnector() {
	        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
	        connector.setScheme("http");
	        connector.setPort(8082);
	        connector.setSecure(false);
	        connector.setRedirectPort(8443);
	        return connector;
	    }
     */
}
