package com.chainsys.munchmate.dao;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.chainsys.munchmate.mapper.HotelMapper;
import com.chainsys.munchmate.mapper.UserId;
import com.chainsys.munchmate.mapper.UserMapper;
import com.chainsys.munchmate.mapper.UserNameMapper;
import com.chainsys.munchmate.model.Cart;
import com.chainsys.munchmate.model.Food;
import com.chainsys.munchmate.model.Hotel;
import com.chainsys.munchmate.model.User;
import com.chainsys.munchmate.mapper.CartMapper;
import com.chainsys.munchmate.mapper.FoodImageMapper;
import com.chainsys.munchmate.mapper.FoodSearchAdmin;
import com.chainsys.munchmate.mapper.PurchaseMapper;
import com.chainsys.munchmate.mapper.FoodMapper;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

@Repository("userDAO")
public class UserImpl implements UserDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	UserMapper mapper;
	CartMapper cart;

	@Override
	// userdetailsinsert
	public void save(User user) {
		String save = "insert into user (name,phone_number,password,city, mail_id)values(?,?,?,?,?)";
		Object[] params = { user.getName(), user.getPhoneNumber(), user.getPassword(), user.getCity(),
				user.getMailId() };
		@SuppressWarnings("unused")
		int noOfRows = jdbcTemplate.update(save, params);
	}

	// update userProfile
	@Override
	public void updateUserDetails(String name, String mailId, String newPhoneNumber) {
		String updateQuery = "UPDATE user SET mail_id = ?, phone_number = ? WHERE name = ?";
		Object[] params = { name, mailId, newPhoneNumber };
		jdbcTemplate.update(updateQuery, params);
	}

//getall user details from user table
	@Override

	public List<User> getAllUsers() {
		String query = "SELECT userid, name, phone_number, city, mail_id FROM user WHERE status = 0";
		List<User> users = jdbcTemplate.query(query, new UserMapper());
		return users;
	}

	/*
	 * // unknow
	 * 
	 * @Override public void updateUser(User user) { String updateQuery =
	 * "UPDATE user SET name = ?, phone_number = ?, city = ?, mail_id = ? WHERE userid = ?"
	 * ; Object[] params = { user.getName(), user.getPhoneNumber(), user.getCity(),
	 * user.getMailId(), user.getUserId() }; jdbcTemplate.update(updateQuery,
	 * params); }
	 */
	@Override
	public void hotelRegistration(Hotel hotel) {
		String save = "insert into hotel(name,location,image,phonenumber,email,password,status)values(?,?,?,?,?,?,?)";
		Object[] params = { hotel.getHotelName(), hotel.getHotelLocation(), hotel.getHotelImage(),
				hotel.getHotelPhoneNumber(), hotel.getHotelEmail(), hotel.getHotelPassword(), hotel.getStatus() };
		@SuppressWarnings("unused")
		int noOfRows = jdbcTemplate.update(save, params);
		System.out.println("in DAO -save");
	}

	@Override
	public boolean login(String mailId, String password) {
		String sql = "SELECT name FROM user WHERE mail_id = ? AND password = ?";
		Object[] params = { mailId, password };
		try {
			@SuppressWarnings("deprecation")
			String name = jdbcTemplate.queryForObject(sql, params, String.class);
			return name != null;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}

	public void deleteUser(int userId) {
		String deleteQuery = "UPDATE user SET status = 1 WHERE userid = ?";
		jdbcTemplate.update(deleteQuery, userId);
	}

	public int getUserID(String email) {
		String query = "Select userid from user where mail_id = ?";
		System.err.println("in the get userid --->" + email);

		return jdbcTemplate.queryForObject(query, new UserId(), email);
	}

	@Override
	public String getUserName(String email) {
		String query = "Select name from user where mail_id = ?";
		System.err.println("in the get userid --->" + email);

		return jdbcTemplate.queryForObject(query, new UserNameMapper(), email);

	}

	public List<Hotel> getAllHotels() {
		String query = "select * from hotel where status=0";
		List<Hotel> hotel = jdbcTemplate.query(query, new HotelMapper());
		return hotel;

	}

	/*
	 * // this is stream method apply public List<Hotel> getAllHotels() { String
	 * query = "select * from hotel where status=0"; List<Hotel> hotels =
	 * jdbcTemplate.query(query, new HotelMapper()); return
	 * hotels.stream().filter(hotel ->
	 * hotel.getStatus().equals("approved")).collect(Collectors.toList()); }
	 */
	@Override
	public Hotel getHotelById(int hotelId) {
		String query = "SELECT hotelid,name,location,image,phonenumber,email FROM hotel WHERE hotelid = ?";
		try {
			return jdbcTemplate.queryForObject(query, new Object[] { hotelId }, new HotelMapper());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void updateHotel(Hotel hotel) {
		String updateQuery = "UPDATE hotel SET status = ? WHERE hotelid = ?";
		Object[] params = { hotel.getStatus(), hotel.getHotelId() };
		jdbcTemplate.update(updateQuery, params);
	}

	@Override
	public Hotel getHotelByEmail(String email) {
		String query = "SELECT hotelid, name, location, image, phonenumber, email, password, status FROM hotel WHERE email = ?";

		try {
			return jdbcTemplate.queryForObject(query, new Object[] { email }, new HotelMapper());
		} catch (IncorrectResultSizeDataAccessException ex) {
			return null;
		}
	}

	@Override
	public void insertFood(Food food) {
		String save = "insert into food(hotelid,hotelname,image,name,price,quantity,catagories,mealtime)values(?,?,?,?,?,?,?,?)";

		Object[] params = { food.getHotelId(), food.getHotelName(), food.getFoodImage(), food.getFoodName(),
				food.getFoodPrice(), food.getFoodQuantity(), food.getFoodCategories(), food.getFoodSession() };

		int noOfRows = jdbcTemplate.update(save, params);

		System.out.println("in DAO -save");
	}

	public List<Food> getFoodsByHotelId(int hotelId) {
		System.out.println("hotelId - " + hotelId);
		String query = "SELECT foodid,hotelid,hotelname ,image,name ,price,quantity,catagories,mealtime FROM food WHERE hotelid = ?";
		try {
			return jdbcTemplate.query(query, new FoodMapper(), hotelId);
		} catch (EmptyResultDataAccessException ex) {
			return Collections.emptyList();
		}

	}

	public List<Food> getAllFoods() {
		String query = "SELECT foodid,hotelid,hotelname,image,name,price,quantity,catagories,mealtime FROM food";
		return jdbcTemplate.query(query, new FoodMapper());
	}

	public void deleteHotel(int hotelId) {
		String deleteQuery = "UPDATE hotel SET action = 1 WHERE hotelid = ?";
		jdbcTemplate.update(deleteQuery, hotelId);
	}

	public void addToCart(Cart cartItem) {
		String save = "insert into cart(userid,hotelid,foodid,foodname,quantity,totalprice,mealtime,currentdate,price)values(?,?,?,?,?,?,?,?,?)";
		Object[] params = { cartItem.getUserId(), cartItem.getHotelId(), cartItem.getFoodId(), cartItem.getFoodName(),
				cartItem.getQuantity(), cartItem.getTotalPrice(), cartItem.getFoodSession(),
				cartItem.getCurrentdate() ,cartItem.getFoodPrice()};
		System.out.println("insert price value:"+cartItem.getFoodPrice());
		int noOfRows = jdbcTemplate.update(save, params);
		System.out.println("in DAO -save");

	}

	public List<Cart> viewCart(int userId) {
		System.out.println("userId - " + userId);
		/*
		 * String query =
		 * "SELECT userid,foodid,foodname,quantity,totalprice,mealtime FROM cart WHERE userid = ? and active=0"
		 * ;
		 */
		/*
		 * String query = "SELECT \r\n" + "    c.userid,\r\n" + "    c.foodid,\r\n" +
		 * "    c.foodname,\r\n" + "    c.quantity,\r\n" + "    c.totalprice,\r\n" +
		 * "    c.mealtime,\r\n" +
		 * "    f.image  -- Select the image column from the food table\r\n" +
		 * "FROM \r\n" + "    cart c\r\n" + "JOIN \r\n" +
		 * "    food f ON c.foodid = f.foodid\r\n" + "WHERE \r\n" +
		 * "    c.userid = ?  -- Replace ? with the actual userid you are querying for\r\n"
		 * + "    AND c.active = 0;\r\n" + "";
		 * 
		 */	
		String query="        SELECT c.userid, c.foodid, c.foodname,\r\n"
				+ "				  c.quantity,  c.totalprice,  c.mealtime,\r\n"
				+ "				   f.image  FROM     cart c\r\n"
				+ "				JOIN food f ON c.foodid = f.foodid WHERE \r\n"
				+ "				   c.userid = ?\r\n"
				+ "				   AND c.active = 0;\r\n"
				+ "";
		
		return jdbcTemplate.query(query, new CartMapper(), userId);

	}

	@Override
	public Food getBase64FoodImage(int foodid) {
		String query = "SELECT image FROM food WHERE foodid = ?";
		return jdbcTemplate.queryForObject(query, new FoodImageMapper(), new Object[] { foodid });
	}

	public void removeCartItem(int foodId) {
		String deleteQuery = "UPDATE cart SET active = 1 WHERE foodid = ?";
		jdbcTemplate.update(deleteQuery, foodId);
	}

	public void updateCartItemQuantity(int foodId, int quantity) {
		System.out.println("foodid:updatequantity" + foodId);
		String updateQuery = "UPDATE cart SET quantity = ? ,totalprice=?*price WHERE foodid = ?";
		jdbcTemplate.update(updateQuery, quantity,quantity, foodId);
	}

	@Override

	public void updateCartItemPrice(int foodId, double price) {

		String updateQuery = "UPDATE cart SET totalprice = ? WHERE foodid = ?";
		jdbcTemplate.update(updateQuery, price, foodId);
	}

	/*
	 * public void updateCartItemQuantity(int foodId, int quantity) { int
	 * currentQuantity = getFoodQuantity(foodId); int newQuantity = currentQuantity
	 * - quantity;
	 * 
	 * String updateQuery = "UPDATE cart SET quantity = ? WHERE foodid = ?";
	 * jdbcTemplate.update(updateQuery, newQuantity, foodId); }
	 * 
	 */
	public List<Food> getFoodQuantity(int foodId) {
		String updateQuery = "select quantity from food where foodid=?";
		System.err.println("12345 " + foodId);
		return (List<Food>) jdbcTemplate.queryForObject(updateQuery, new FoodMapper(), foodId);

	}

	public void updateFoodQuantityDecrease(int foodId, int quantity) {

		String updateQuery = "UPDATE food SET quantity = ? WHERE foodid = ?";
		jdbcTemplate.update(updateQuery, quantity, foodId);
	}

	@Override
	public List<Food> searchFood(String foodName) {
		String query = "SELECT foodid,hotelid,hotelname,image,name,price,quantity,catagories,mealtime FROM food WHERE name LIKE ?";
		try {
			return jdbcTemplate.query(query, new FoodMapper(), "%" + foodName + "%");
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	@Override
	public void updateFoodPrice(int foodId, int newPrice) {
		String sql = "UPDATE food SET price = ? WHERE foodid = ?";
		jdbcTemplate.update(sql, newPrice, foodId);
	}

	@Override
	public void updateFoodQuantity(int foodId, int newQuantity) {
		String sql = "UPDATE food SET quantity = ? WHERE foodid = ?";
		jdbcTemplate.update(sql, newQuantity, foodId);
	}

	@Override

	public List<Cart> orderView(int hotelId) {
		System.out.println("hotelID: - " + hotelId);
		/*
		 * String query =
		 * "SELECT userid,hotelid,foodid,foodname,quantity,totalprice,mealtime ,currentdate,payment ,delivery_status ,priceFROM cart WHERE hotelid = ? and active=0"
		 * ;
		 */		String query = "SELECT userid,hotelid,foodid,foodname,quantity,totalprice,mealtime ,currentdate,delivery_status,price,payment FROM cart WHERE hotelid = ? and active=0";

		System.out.println(jdbcTemplate.query(query, new PurchaseMapper(), hotelId));
		return jdbcTemplate.query(query, new PurchaseMapper(), hotelId);

	}

	public void updateOrders(int userId) {
		String insertOrderQuery = "INSERT INTO orders (hotelid, userid, foodname, address, totalamount, paymentmode, orderdelivery) "
				+ "SELECT " + "    c.hotelid, " + "    c.userid, " + "    f.name AS foodname, " + "    u.address, "
				+ "    SUM(c.totalprice) AS totalamount, " + "    'Cash' AS paymentmode, "
				+ "    'Standard delivery' AS orderdelivery " + "FROM " + "    cart c " + "INNER JOIN "
				+ "    user u ON c.userid = u.userid " + "INNER JOIN " + "    food f ON c.foodid = f.foodid " + "WHERE "
				+ "    c.active = 0 " + "    AND u.userid = ? " + "GROUP BY "
				+ "    c.hotelid, c.userid, f.name, u.address";

		jdbcTemplate.update(insertOrderQuery, userId);
	}

	@Override
	public void paymentUpdate(int userId, String status) {
		String deleteQuery = "UPDATE cart SET payment = ? WHERE userid = ?";
		jdbcTemplate.update(deleteQuery, status, userId);
	}

	@Override
	public void deleteFood(int foodId) {
		System.out.println(foodId);
		String deleteQuery = "DELETE FROM food WHERE foodid = ?";
		jdbcTemplate.update(deleteQuery, foodId);
	}

	@Override
	public List<Food> searchFoodByName(String foodName) {
		String query = "SELECT hotelname, image, name, price, quantity, catagories, mealtime FROM food WHERE name LIKE ?";
		return jdbcTemplate.query(query, new FoodSearchAdmin(), "%" + foodName + "%");
	}

	@Override
	public void foodQuantityDecrease(int foodId, int addedQuantity) {
		String query = "SELECT quantity from food where foodid=?";
		int quantity = jdbcTemplate.queryForObject(query, Integer.class, foodId) - addedQuantity;
		updateFoodQuantityDecrease(foodId, quantity);
	}

	public void updateDeliveryStatus(Cart cartItem) {
		String updateQuery = "UPDATE cart SET delivery_status = ? WHERE hotelid = ? and foodname=?";
		Object[] params = { cartItem.getDeliveryStatus(), cartItem.getHotelId(), cartItem.getFoodName() };
		jdbcTemplate.update(updateQuery, params);
	}

	@Override
	public List<Cart> getDeliveredOrders() {
		String query = "SELECT userid, hotelid, foodid, foodname, quantity, totalprice, mealtime, currentdate,price, payment, delivery_status FROM cart WHERE active = 0";
		return jdbcTemplate.query(query, new PurchaseMapper());
	}

	@Override
	public List<Food> getFoodsByMealTime(String mealTime) {
		String query = "SELECT foodid, hotelid, hotelname, image, name, price, quantity, catagories, mealtime "
				+ "FROM food " + "WHERE mealtime = ?";
		try {
			return jdbcTemplate.query(query, new FoodMapper(), mealTime);
		} catch (EmptyResultDataAccessException ex) {
			return Collections.emptyList();
		}
	}

	@Override

	public int getFoodPrice(int foodId) {
		String query = "SELECT price FROM food WHERE foodid = ?";
		Integer price = jdbcTemplate.queryForObject(query, Integer.class, foodId);
		return price != null ? price : 0;
	}
}
