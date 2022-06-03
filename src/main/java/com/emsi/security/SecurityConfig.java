package com.emsi.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter 
{
	@Autowired
	

	@Override
	public  void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
		authenticationManagerBuilder.inMemoryAuthentication()
				.withUser("traibiz")
				.password("{noop}traibiz")
				.roles("ADMIN")
				.and()
				.withUser("user")
				.password("{noop}user")
				.roles("USER");
	}
	 
	@Override 
	protected void configure(HttpSecurity http) throws Exception
	{
		http.csrf().disable();
		 
		http.authorizeRequests().antMatchers("/login").permitAll();

		
		http.authorizeRequests().antMatchers("/users/**").hasRole("ADMIN");
		http.authorizeRequests()
		.antMatchers("/404","/statistiques","/rapports","/dossiers","/","/clients","/fournisseurs")
		.hasAnyRole("USER","ADMIN");
		
		http.formLogin().loginPage("/login");  
		http.exceptionHandling().accessDeniedPage("/403"); 
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
      web
        .ignoring()
           .antMatchers("/resources/**"); // #3
    }
	 
}











