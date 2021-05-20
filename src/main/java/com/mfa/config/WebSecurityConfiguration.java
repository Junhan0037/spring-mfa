package com.mfa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackages = {"com.mfa.repositories"})
@EntityScan(basePackages = {"com.mfa.datas"}, basePackageClasses = {Jsr310Converters.class})
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/i18n/**")
                .mvcMatchers("/static/**")
                .mvcMatchers("/css/**")
                .mvcMatchers("/js/**")
                .mvcMatchers("/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        String permitAllUrl = "/login,/,/prelogin,/mfator,/purelogin";
        http.headers().frameOptions().sameOrigin()
                .and().authorizeRequests().antMatchers(permitAllUrl.split(",")).permitAll()
                .and().formLogin().loginPage("/login")
                .and().logout().logoutUrl("/logout").invalidateHttpSession(false).permitAll()
                .and().authorizeRequests().anyRequest().authenticated();
    }

}
