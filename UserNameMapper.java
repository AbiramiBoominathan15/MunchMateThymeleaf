package com.chainsys.munchmate.mapper;

import java.sql.ResultSet;


import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.munchmate.model.User;
public class UserNameMapper implements RowMapper<String> {

	    @Override
	    public String mapRow(ResultSet rs, int rowNum) throws SQLException {  	
	    
	    	
	    	User user = new User();
	     
	    	user.setName(rs.getString("name"));
	        return  user.getName();
	    
	    }

}
