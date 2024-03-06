package eaismart.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Iekiny Marcel
 * Jul 13, 2020
 */
@EnableWebSecurity
public class SecurityConfiguration extends  WebSecurityConfigurerAdapter{ 
	
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			//super.configure(auth); 
			auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
			// .and() 
			auth.inMemoryAuthentication().withUser("demo").password(passwordEncoder().encode("demo")).roles("IGRP");
			//auth.authenticationProvider(authenticationProvider()); // via data base 
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception { 
			http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/css/*" , "/img/**", "/js/**", "/node_modules/**", "/vendors/**", "/scss/*").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.defaultSuccessUrl("/home")
			.loginPage("/login")
			//.failureUrl("")
			.permitAll()
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
		} 
		
		@Bean
		PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		} 
}