package com.mfa.config;

import com.mfa.config.filter.CustomUsernamePasswordAuthenticationFilter;
import com.mfa.config.filter.PreUsernamePasswordAuthenticationFilter;
import com.mfa.config.handler.FailureHandler;
import com.mfa.config.handler.SuccessHandler;
import com.mfa.config.provider.CustomDaoAuthenticationProvider;
import com.mfa.services.CustomUserDetailsService;
import com.mfa.services.MfaService;
import com.mfa.services.UserService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackages = {"com.mfa.repositories"})
@EntityScan(basePackages = {"com.mfa.datas"}, basePackageClasses = {Jsr310Converters.class})
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;
    private final MfaService mfaService;

    public WebSecurityConfiguration(CustomUserDetailsService customUserDetailsService, UserService userService, MfaService mfaService) {
        this.customUserDetailsService = customUserDetailsService;
        this.userService = userService;
        this.mfaService = mfaService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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

        http.addFilterBefore(new PreUsernamePasswordAuthenticationFilter(passwordEncoder(), userService, mfaService), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(customDaoAuthenticationProvider());

        http.cors().and().csrf().disable();

        String permitAllUrl = "/login,/,/prelogin,/mfator,/purelogin";
        http.headers().frameOptions().sameOrigin()
                .and().authorizeRequests().antMatchers(permitAllUrl.split(",")).permitAll()
                .and().formLogin().loginPage("/login")
                .and().logout().logoutUrl("/logout").invalidateHttpSession(false).permitAll()
                .and().authorizeRequests().anyRequest().authenticated();
    }

    private CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter() throws Exception {
        CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter = new CustomUsernamePasswordAuthenticationFilter(this.authenticationManagerBean());
        customUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(new SuccessHandler());
        customUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(new FailureHandler());

        return customUsernamePasswordAuthenticationFilter;
    }

    private CustomDaoAuthenticationProvider customDaoAuthenticationProvider() {
        CustomDaoAuthenticationProvider customDaoAuthenticationProvider = new CustomDaoAuthenticationProvider(customUserDetailsService);
        customDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return customDaoAuthenticationProvider;
    }

}
