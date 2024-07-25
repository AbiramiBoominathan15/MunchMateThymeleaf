package com.chainsys.munchmate.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import com.chainsys.munchmate.model.Cart;

public class PurchaseMapper implements RowMapper<Cart> {
	public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
		Cart cartItem = new Cart();
		cartItem.setUserId(rs.getInt("userId"));
		cartItem.setHotelId(rs.getInt("hotelid"));
		cartItem.setFoodId(rs.getInt("foodid"));
		cartItem.setFoodName(rs.getString("foodname"));
		cartItem.setQuantity(rs.getInt("quantity"));
		cartItem.setTotalPrice(rs.getInt("totalprice"));
		cartItem.setFoodSession(rs.getString("mealtime"));
		cartItem.setCurrentdate(LocalDate.parse(rs.getString("currentdate")));
		cartItem.setFoodPrice(rs.getInt("price"));
		cartItem.setPaymentStatus(rs.getString("payment"));
		cartItem.setDeliveryStatus(rs.getString("delivery_status")); // Keep as String

		return cartItem;
	}
}