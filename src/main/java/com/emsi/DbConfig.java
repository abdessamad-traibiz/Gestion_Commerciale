package com.emsi;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.dbcp.BasicDataSource; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
	public class DbConfig { 
	    @Bean
	    public  BasicDataSource dataSource() {



			BasicDataSource dataSource=new BasicDataSource();
			dataSource.setUrl("jdbc:mysql://localhost/gestioncommerciale");
			dataSource.setUsername("root");
			dataSource.setPassword("");
			return dataSource;

	    }
	}
