package dev.rkorn.services;

import dev.rkorn.entities.Restaurant;
import dev.rkorn.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getAuthority(String username){
        String sql = "SELECT authority FROM Authorities WHERE username=?";

        String authority = (String) jdbcTemplate.queryForObject(
                sql, new Object[] { username }, String.class);
        // System.out.println(authority);
        return authority;
    }
}
