package dev.rkorn.services;
import dev.rkorn.entities.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RestaurantService {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public RestaurantService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Restaurant> index() {
        return jdbcTemplate.query("SELECT * FROM Restaurants ORDER BY NAME", new BeanPropertyRowMapper<>(Restaurant.class));
    }

    public Restaurant show(int id) {
            //
        return jdbcTemplate.query(
                        "SELECT * FROM Restaurants WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Restaurant.class))
                .stream().findAny().orElse(null);
    }

    public void save(Restaurant restaurant) {

        jdbcTemplate.update("INSERT INTO Restaurants (name, dishes) VALUES (?,?)", restaurant.getName(), restaurant.getDishes());
    }

    public void update(int id, Restaurant updatedRestaurant) {
        //for restaurant update code
        jdbcTemplate.update(
                "UPDATE Restaurants SET name=?, dishes=? WHERE id=?",
                updatedRestaurant.getName(), updatedRestaurant.getDishes(), id);


    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Restaurants WHERE id=?", id);

    }
}