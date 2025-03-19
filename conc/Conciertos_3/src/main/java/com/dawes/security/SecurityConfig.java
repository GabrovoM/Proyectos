package com.dawes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.dawes.serviciosImpl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	 @Autowired
	 private UserDetailsServiceImpl userDetailsService;
	 
	 @Bean
	 public UserDetailsServiceImpl userDetailsService(){
	     return new UserDetailsServiceImpl();
	 }
	
	 @Bean
	 public BCryptPasswordEncoder passwordEncoder(){
	     return new BCryptPasswordEncoder();
	 }
	 
	 @Bean
	 public DaoAuthenticationProvider authenticationProvider(){
	     DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	     authenticationProvider.setUserDetailsService(userDetailsService);
	     authenticationProvider.setPasswordEncoder(passwordEncoder());
	     return authenticationProvider;
	 }
	 
	 @Bean
	 public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
	     AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);	        
	     authenticationManagerBuilder.authenticationProvider(authenticationProvider());
	     return authenticationManagerBuilder.build();
	 }
	 
	 @Bean
	    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity.authorizeHttpRequests(
                 pathRequested -> pathRequested
//                         .shouldFilterAllDispatcherTypes(false)
                         .requestMatchers("/webjars/**", "/css/**", "/js/**").permitAll() 
                         .requestMatchers("/img/**").permitAll()
                         .requestMatchers("/api/**").permitAll()  // Rest controller				
         )
         		 .csrf(csrf -> csrf.disable())  // para realizar peticiones POST - Rest controller
                 .authorizeHttpRequests(requests ->
                         requests.requestMatchers("/", "/login", "/logout", "/index", "/registrarse", "/conciertos/fechas/submit").permitAll())

                 .authorizeHttpRequests(request -> request
                                 .requestMatchers("/registrado").hasAnyRole("USER", "ADMIN")
                                 .requestMatchers("/admin", "/conciertos").hasRole("ADMIN")
                           ////      .requestMatchers("/admin/**").hasRole("ADMIN")
                                 .anyRequest().authenticated()
                 )

                 // Usuario no autorizado: está registrado en base de datos pero sin permisos para el recurso
                 // al que intenta acceder
                 .exceptionHandling(handling -> handling.accessDeniedPage("/403"))
                 .formLogin(form -> form
                                 .loginProcessingUrl("/j_spring_security_check") // Submit URL
                                 .loginPage("/login")
                                 .failureUrl("/login?error=true")
                                 .usernameParameter("username")
                                 .passwordParameter("password")
//                     .defaultSuccessUrl("/admin", true)
                                 .defaultSuccessUrl("/redirectByRole", true)
                                 
                 );	    	
	    	return httpSecurity.build();
	    }

}
