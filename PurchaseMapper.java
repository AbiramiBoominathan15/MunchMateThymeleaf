package com.chainsys.munchmate.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import com.chainsys.munchmate.model.Cart;

/*
 * public class PurchaseMapper implements RowMapper<Cart> { public Cart
 * mapRow(ResultSet rs, int rowNum) throws SQLException { Cart cartItem = new
 * Cart(); int userId = rs.getInt(1); int hotelid = rs.getInt(2); int foodid =
 * rs.getInt(3); String foodname = rs.getString(4); int quantity = rs.getInt(5);
 * int totalprice = rs.getInt(6); String foodSession = rs.getString(7);
 * LocalDate currentdate = LocalDate.parse(rs.getString(8)); String
 * deliveryStatus = rs.getString(9); int price = rs.getInt(10); String
 * paymentStatus = rs.getString(11);
 * 
 * cartItem.setUserId(userId); cartItem.setHotelId(hotelid);
 * cartItem.setFoodId(foodid); cartItem.setFoodName(foodname);
 * cartItem.setQuantity(quantity); cartItem.setTotalPrice(totalprice);
 * cartItem.setFoodSession(foodSession); cartItem.setCurrentdate(currentdate);
 * cartItem.setDeliveryStatus(deliveryStatus); cartItem.setFoodPrice(price);
 * cartItem.setPaymentStatus(paymentStatus);
 * 
 * System.out.println("PurchaseMapper:----" + price);
 * System.out.println(deliveryStatus);
 * 
 * return cartItem; }
 * 
 * }
 * 
 */
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
