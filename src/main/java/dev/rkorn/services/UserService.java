package dev.rkorn.services;

import dev.rkorn.entities.Restaurant;
import dev.rkorn.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserService {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public UserService(JdbcTemplate jdbcTemplate, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String getAuthority(String username){
        String sql = "SELECT authority FROM Authorities WHERE username=?";

        String authority = (String) jdbcTemplate.queryForObject(
                sql, new Object[] { username }, String.class);
        return authority;
    }

    public void save(User user) {
        jdbcTemplate.update("INSERT INTO Users (username, password) values (?,?)", user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()));
        jdbcTemplate.update("INSERT INTO Authorities (username, authority)  values (?, 'ROLE_USER')", user.getUsername());
    }
}
