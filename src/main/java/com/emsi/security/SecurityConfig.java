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
	@Autowired private DataSource dataSource; 
	
	@Override 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{ 
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select username as principal, password as credentials, active from user where username=?")
		.authoritiesByUsernameQuery("select username as principal, role from users_roles ur,role r where ur.username=? and ur.role_id=r.id")
		.rolePrefix("ROLE_")
		.passwordEncoder(new BCryptPasswordEncoder());
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











