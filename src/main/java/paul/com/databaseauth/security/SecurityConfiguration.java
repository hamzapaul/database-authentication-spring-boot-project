package paul.com.databaseauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import paul.com.databaseauth.services.MyUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final MyUserDetailService myUserDetailService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public SecurityConfiguration(MyUserDetailService myUserDetailService, PasswordEncoder passwordEncoder) {
        this.myUserDetailService = myUserDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    //Authentication
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(myUserDetailService);
        return provider;
    }

    //Authorization Setup
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/").permitAll()
                .and().formLogin()
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) //delete this line if csrf enable
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl("/");
        return http.build();
    }
}
