package com.belousov.reddit.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/api/auth/**")
        .permitAll()
        .anyRequest()
        .authenticated();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JavaMailSender javaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.mailtrap.io");
    mailSender.setPort(2525);

    mailSender.setUsername("2bf476315f5e79");
    mailSender.setPassword("68048bda733595");

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    //props.put("mail.debug", "true");

    return mailSender;
  }

}
