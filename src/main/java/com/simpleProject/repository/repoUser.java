package com.simpleProject.repository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpleProject.entity.UserDetails;

import com.simpleProject.exception.UserNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class repoUser {

    private JdbcTemplate jdbcTemplate;
    private static ObjectMapper objectMapper;



    public repoUser(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }




    public int saveUser(UserDetails details) {
        String id = UUID.randomUUID().toString();
               details.setId(id);
        String jsonDetails = convertMapToJson(details.getAdditionalDetails());
        String sql = "INSERT INTO springjdbc (id, name, description, created_at, is_active, amount, additional_details) " +
                "VALUES (?,?, ?, ?, ?, ?, ?::jsonb)";
        return jdbcTemplate.update(sql,details.getId(), details.getName(), details.getDescription(), details.getCreatedAt(),
                details.getIsActive(), details.getAmount(),jsonDetails );

    }
    private String convertMapToJson(Map<String, Object> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to convert map to JSON", e);
        }
    }
    public Object getUserIDById(String id) {
        String sql = "SELECT * FROM springjdbc WHERE id = ?";
        try {
            UserDetails userDetails = jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserDetailsRowMapper());
            return userDetails;
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("This id is not present in the database");
        }
    }


    public List<UserDetails> getAllUserDetails() {
        String sql = "SELECT * FROM springjdbc";
        return jdbcTemplate.query(sql, new UserDetailsRowMapper());
    }
    public int updateEntity( String id ,UserDetails userDetails) {
        String jsonDetails = convertMapToJson(userDetails.getAdditionalDetails());
        String sql = "UPDATE springjdbc SET name = ?, description = ?, created_at = ?, is_active = ?, amount = ?, additional_details = ?::jsonb WHERE id = ?";
        int update = jdbcTemplate.update(sql, userDetails.getName(), userDetails.getDescription(), userDetails.getCreatedAt(),
                userDetails.getIsActive(), userDetails.getAmount(), jsonDetails, userDetails.getId());

        if(update==0){
            throw new UserNotFoundException("This id is not present in the database");
        }
        return update;
    }

    public int deleteUser(String id) {

        String sql = "DELETE FROM springjdbc WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);

        if (rowsAffected == 0) {
            throw new UserNotFoundException("This id is not present in the database");
        }

        return rowsAffected;


    }

    private static class UserDetailsRowMapper implements RowMapper<UserDetails> {
        @Override
        public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

            UserDetails mapTo = new UserDetails();
            mapTo.setId(rs.getString("id"));
            mapTo.setName(rs.getString("name"));
            mapTo.setDescription(rs.getString("description"));
            mapTo.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            mapTo.setAdditionalDetails(convertJsonbToMap(rs.getString("additional_details")));

            return mapTo;
        }
    }
    private  static Map<String, Object> convertJsonbToMap(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to convert JSON to Map", e);
        }
    }

}

