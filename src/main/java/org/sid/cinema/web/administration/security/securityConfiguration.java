package org.sid.cinema.web.administration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class securityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //passwordEncoder.encode("123")
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).
                usersByUsernameQuery("select users.username as username, users.password as password, users.enabled as enabled \n" +
                        "  from users where users.username=?")
                .authoritiesByUsernameQuery("SELECT users.username as username, roles.name as role FROM users \n" +
                        "INNER JOIN user_role ON users.id = user_role.user_id\n" +
                        "INNER JOIN roles ON user_role.role_id = roles.id\n" +
                        "WHERE users.username = ?")
//                .authoritiesByUsernameQuery("SELECT user.username as principal,role.name as role FROM role, user,user_role WHERE user.username = ? and (role.id LIKE user_role.role_id and user.id LIKE user_role.user_id )" +
//                        "GROUP BY role.name")
                .passwordEncoder(passwordEncoder)
                .rolePrefix("ROLE_");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] apiUrls = new String[]{
                "/payerTickets/**"
        };

        http.formLogin().loginPage("/login").defaultSuccessUrl("/cinemasList")
        ;
        http.authorizeRequests().antMatchers("/save**/**", "/delete**/**", "/update**/**", "/edit**/**","/new**/**").hasRole("ADMIN");
//        http.authorizeRequests().antMatchers("/**").hasRole("USER");
//        http.authorizeRequests().anyRequest().authenticated();
        http.csrf().ignoringAntMatchers(apiUrls);

        http.exceptionHandling().accessDeniedPage("/notAuthorized");
    }

}
