package com.emsi;

import com.emsi.conversions.ClientConverter;
import com.emsi.conversions.FournisseurConverter;
import com.emsi.imetier.IClientMetier;
import com.emsi.imetier.IFournisseurMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
 

@SpringBootApplication
@ServletComponentScan
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Configuration
	static class ConfigFormatter extends WebMvcConfigurerAdapter {
		@Autowired private IClientMetier mc;
		@Autowired private IFournisseurMetier mf;
	    @Override
	    public void addFormatters(FormatterRegistry registry) { 
	    	registry.addConverter(new ClientConverter(mc));
	    	registry.addConverter(new FournisseurConverter(mf));
	    }
	}
}
