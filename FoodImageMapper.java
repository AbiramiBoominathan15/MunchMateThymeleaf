package com.chainsys.munchmate.mapper;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import org.springframework.jdbc.core.RowMapper;
import com.chainsys.munchmate.model.Food;
public class FoodImageMapper implements RowMapper<Food> {
	public Food mapRow(ResultSet rs, int rowNum) throws SQLException {
		Food food = new Food();
        Blob blob = rs.getBlob("image");
        if (blob != null) {
            int blobLength = (int) blob.length();    
            byte[] blobAsBytes = blob.getBytes(1, blobLength);
            food.setBase64Image(Base64.getEncoder().encodeToString(blobAsBytes));
        }

        return food;
    }

		}
