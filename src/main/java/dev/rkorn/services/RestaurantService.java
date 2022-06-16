package dev.rkorn.services;
import dev.rkorn.entities.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
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

    public String select(String username,int id) throws Exception {
        try{
            jdbcTemplate.execute("do $$ \n" +
                    "declare\n" +
                    "eee bool;\n" +
                    "BEGIN\n" +
                    "select  to_char(current_timestamp, 'HH24:MI:SS') > to_char(timestamp '2002-04-20 11:00:00.00', 'HH24:MI:SS') into eee; \n" +
                    "if eee = true then  raise exception 'Нельзя изменить выбор после 11:00:00 !'; end if;\n" +
                    "end ;\n" +
                    "$$ LANGUAGE plpgsql;\n");
        }
        catch (Exception e){
            throw new Exception(e);
        }
        String sql = "select COALESCE((select 1 from user_restaurants where username = ?),0) is_exists";
        Integer restaurantID = (Integer) jdbcTemplate.queryForObject(
                sql, new Object[] { username }, Integer.class);
        if (restaurantID==0) jdbcTemplate.update(
                "insert into user_restaurants (username, restaurants_id) values (?,?)", username, id);

        else {
            jdbcTemplate.update("update user_restaurants SET restaurants_id=? where username=?", id, username);
        }
        System.out.println("Debug:"+restaurantID);
        String restaurantName = show(id).getName();
        return restaurantName;
    }


}