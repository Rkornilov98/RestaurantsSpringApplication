package dev.rkorn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.antMatchers("/").authenticated()
                .antMatchers("/restaurants/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .and()
                .formLogin()
                .and()
                .logout();
    }


    @Bean
    public JdbcUserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        //$2a$12$ZUZ0JxibgaxrWvz8dgY50u5ptPjI5xY3pf3.zvce2xbhK7Q8R/6aS user
        //$2a$12$GS48oVTSLDT.6eCieWr7de89bGeuPTV.oduFDtSUb/WUgFS.TJr.i admin
        return jdbcUserDetailsManager;
    }
}
