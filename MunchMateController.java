package com.chainsys.munchmate.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.chainsys.munchmate.dao.UserDAO;
import com.chainsys.munchmate.model.Cart;
import com.chainsys.munchmate.model.Food;
import com.chainsys.munchmate.model.Hotel;
import com.chainsys.munchmate.model.User;
import jakarta.servlet.http.HttpSession;

@Controller
public class MunchMateController {
	@Autowired
	UserDAO userDao;

	@RequestMapping("/index")
	public static String home() {
		return "index";
	}

	@RequestMapping("/login")
	public static String login() {
		return "loginPage";
	}

	@RequestMapping("/aboutPageUser")
	public static String about() {
		return "aboutPageUser.html";
	}

	@RequestMapping("/contactPageUser")
	public static String contact() {
		return "contactPageUser.html";
	}

	@RequestMapping("/signup")
	public static String register() {
		return "signup";
	}

	@RequestMapping("/hotelmenu")
	public static String menu() {
		return "hotelmenu";
	}

	@RequestMapping("/hotelAdminDashboard")
	public static String homePage() {
		return "hotelAdminDashboard";
	}
	@RequestMapping("/payment")
	public String processPayment(@RequestParam("amount") BigDecimal amount, Model model) {
	    model.addAttribute("amount", amount);
	    return "paymentPage";
	}
	/*
	 * @RequestMapping("/menu") public String foodMenu() { return "hotelDetails"; }
	 */
	

	@PostMapping("/register")
	public String saveUser(@RequestParam("name") String name, @RequestParam("password") String password,
			@RequestParam("phonenumber") String phonenumber, @RequestParam("city") String city,
			@RequestParam("email") String email) {
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user.setPhoneNumber(phonenumber);
		user.setCity(city);
		user.setMailId(email);
		System.out.println(
				user.getName() + user.getPassword() + user.getPhoneNumber() + user.getCity() + user.getMailId());
		userDao.save(user);
		return "loginPage";
	}

	@PostMapping("/updateProfile")
	public String updateProfile(@RequestParam("name") String name, @RequestParam("email") String mailId,
			@RequestParam("phonenumber") String newPhoneNumber, Model model) {
		userDao.updateUserDetails(name, mailId, newPhoneNumber);
		return "updateUserProfile.jsp";
	}

	@PostMapping("/hotelregister")
	public String saveHotel(@RequestParam("hotelName") String name, @RequestParam("image") MultipartFile imageFile,
			@RequestParam("phonenumber") String phonenumber, @RequestParam("city") String city,
			@RequestParam("password") String password, @RequestParam("email") String email) throws IOException {
		System.out.println("in register handle");

		if (!imageFile.isEmpty()) {
			byte[] imageBytes = imageFile.getBytes();

			Hotel hotel = new Hotel();
			hotel.setHotelName(name);
			hotel.setHotelImage(imageBytes);
			hotel.setHotelPhoneNumber(phonenumber);
			hotel.setHotelLocation(city);
			hotel.setHotelPassword(password);
			hotel.setHotelEmail(email);
			hotel.setStatus("unapproved");
			System.out.println(hotel.getHotelName() + hotel.getHotelImage() + hotel.getHotelPhoneNumber()
					+ hotel.getHotelLocation() + hotel.getHotelPassword() + hotel.getHotelEmail());
			userDao.hotelRegistration(hotel);

			return "redirect:loginPage";
		} else {
			return "redirect:/error";
		}
	}

	@GetMapping("/listofusers")
	public String listOfUsers(Model model) {
		List<User> users = userDao.getAllUsers();
		model.addAttribute("users", users);
		System.out.println(users);
		return "customerRead";
	}

	@PostMapping("/deleteUser")
	public String deleteUser(@RequestParam("userId") int userId) {
		userDao.deleteUser(userId);
		return "redirect:/listofusers";
	}

	@GetMapping("/hotels")
	public String getAllHotels(Model model) {
		List<Hotel> hotels = userDao.getAllHotels();
		
		model.addAttribute("hotels", hotels);
		for (Hotel hotel : hotels) {
			String base64Image = Base64.getEncoder().encodeToString(hotel.getHotelImage());
			hotel.setBase64Image(base64Image);
		}

		System.out.println(hotels);

		return "hotelDetails";
	}

	@PostMapping("/login")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session) {

		if (email.equals("abiramiboominathan15@gmail.com") && password.equals("abirami@15")) {
			session.setAttribute("email", email);
			return "adminDashboard";
		} else if (userDao.login(email, password)) {
			session.setAttribute("email", email);
			session.setAttribute("userid", userDao.getUserID(email));
			session.setAttribute("name", userDao.getUserName(email));
			return "redirect:/foods";
		} else {
			Hotel hotel = userDao.getHotelByEmail(email);
			if (hotel != null && hotel.getHotelPassword().equals(password)
					&& hotel.getStatus().equalsIgnoreCase("approved")) {
				session.setAttribute("email", email);
				session.setAttribute("hotelId", hotel.getHotelId());
				session.setAttribute("hotelName", hotel.getHotelName());
				return "redirect:hotelAdminDashboard";
			} else {
				session.setAttribute("message", "Your hotel is not yet approved.");
				return "redirect:/loginPage";
			}

		}
	}

	@PostMapping("/updateApproval")
	public String updateApproval(@RequestParam("hotelId") int hotelId, @RequestParam("approved") String approved) {
		Hotel hotel = new Hotel();
		hotel.setHotelId(hotelId);
		hotel.setStatus(approved);
		userDao.updateHotel(hotel);
		return "redirect:/hotels";
	}

	@PostMapping("/foodregister")
	public String saveFood(@RequestParam("hotelid") int hotelId, @RequestParam("hotelname") String hotelName,
			@RequestParam("foodName") String foodName, @RequestParam("foodCategory") String foodCategory,
			@RequestParam("foodSession") String foodSession, @RequestParam("foodPrice") int foodPrice,
			@RequestParam("foodQuantity") int foodQuantity, @RequestParam("image") MultipartFile imageFile, Model model)
			throws IOException {
		System.out.println("in register handle");

		if (!imageFile.isEmpty()) {
			byte[] imageBytes = imageFile.getBytes();
			Food food = new Food();
			food.setHotelId(hotelId);
			food.setHotelName(hotelName);
			food.setFoodName(foodName);
			food.setFoodCategories(foodCategory);
			food.setFoodSession(foodSession);
			food.setFoodPrice(foodPrice);
			food.setFoodQuantity(foodQuantity);
			food.setFoodImage(imageBytes);

			System.out.println(
					food.getHotelName() + food.getFoodName() + food.getFoodCategories() + food.getFoodSession());

			userDao.insertFood(food);

			return "redirect:/foodList";
		} else {
			return "redirect:/error";
		}
	}

	/*
	 * @GetMapping("/foodList") public String showFoodList(Model model, HttpSession
	 * session) { int hotelId = (int) session.getAttribute("hotelId"); List<Food>
	 * foodList = userDao.getFoodsByHotelId(hotelId); model.addAttribute("foodList",
	 * foodList); System.out.println(foodList);
	 * 
	 * return "menu";
	 * 
	 * }
	 */

	/*
	 * @GetMapping("/foodList") public String showFoodList(Model model, HttpSession
	 * session) { int hotelId = (int) session.getAttribute("hotelId"); List<Food>
	 * foodList = userDao.getFoodsByHotelId(hotelId);
	 * 
	 * for (Food food : foodList) { String base64Image =
	 * Base64.getEncoder().encodeToString(food.getFoodImage());
	 * food.setBase64Image(base64Image); }
	 * 
	 * model.addAttribute("foodList", foodList); return "menu"; }
	 */
	@GetMapping("/foodList")
	public String showFoodList(Model model, HttpSession session) {

		int hotelId = (int) session.getAttribute("hotelId");
		List<Food> foodList = userDao.getFoodsByHotelId(hotelId);
		// logic

		for (Food food : foodList) {
			String base64Image = Base64.getEncoder().encodeToString(food.getFoodImage());
			food.setBase64Image(base64Image);
		}
		model.addAttribute("foodList", foodList);

		return "menu"; // Return the Thymeleaf template name
	}

	@GetMapping("/food")
	public String showFoodList(Model model) {
		List<Food> foods = userDao.getAllFoods();
		for (Food food : foods) {
			String base64Image = Base64.getEncoder().encodeToString(food.getFoodImage());
			food.setBase64Image(base64Image);
		}

		model.addAttribute("foods", foods);
		return "food";
	}

	@GetMapping("/foods")
	public String showFoodListt(Model model) {
		int hour = LocalTime.now().getHour();
		String mealTime = "";

		if (hour >= 6 && hour < 12) {
			mealTime = "Breakfast";
		} else if (hour >= 12 && hour < 17) {
			mealTime = "Lunch";
		} else {
			mealTime = "Dinner";
		}

//		List<Food> foods = userDao.getAllFoods();
		List<Food> foods = userDao.getFoodsByMealTime(mealTime);

		for (Food food : foods) {
			String base64Image = Base64.getEncoder().encodeToString(food.getFoodImage());
			food.setBase64Image(base64Image);
		}
		/*
		 * System.out.println("fs--->" + food.getFoodSession());
		 */
		model.addAttribute("foods", foods);

		System.out.println("------>showFoodListt");
		return "addToCart.html";
	}

	@PostMapping("/deleteHotel")
	public String deleteHotel(@RequestParam("hotelId") int hotelId) {
		try {
			userDao.deleteHotel(hotelId);
			return "redirect:/hotels";
		} catch (Exception e) {
			return "redirect:/error";
		}
	}

	@GetMapping("addToCart")
	public String addToCart(@RequestParam("userid") int userId, @RequestParam("foodid") int foodid,
			@RequestParam("hotelid") int hotelid, @RequestParam("foodname") String foodname,
			@RequestParam("quantity") int quantity, @RequestParam("price") int price,
			@RequestParam("mealTime") String foodSession, Model model, HttpSession session) {
		System.err.println("------>");
		session.setAttribute("foodId", foodid);
		session.setAttribute("quantity", quantity);
		System.out.println("food:" + foodSession);
		double totalPrice = price * quantity;
		Food food = userDao.getBase64FoodImage(foodid);

		LocalDate currentDate = LocalDate.now();
		System.out.println("---->" + currentDate);
		Cart cartItem = new Cart();
		cartItem.setCurrentdate(currentDate);
		cartItem.setUserId(userId);
		cartItem.setFoodId(foodid);
		cartItem.setHotelId(hotelid);
		System.out.println(hotelid);
		cartItem.setFoodName(foodname);

		cartItem.setQuantity(quantity);
cartItem.setFoodPrice(price);
		cartItem.setTotalPrice(totalPrice);
		cartItem.setFoodSession(foodSession);
		cartItem.setFoodImage(food.getFoodImage());
		System.out.println("foodprice"+price);

		userDao.addToCart(cartItem);
		/*
		 * userDao.updateQuantities(foodid, quantity);
		 */

		return "redirect:/foods";

	}

	@PostMapping("/payment")
	public String payment(@RequestParam("userid") int userId, HttpSession session) {
		String paymentStatus = "paid";
		int id = (int) session.getAttribute("foodId");
		int quantity = (int) session.getAttribute("quantity");

		userDao.paymentUpdate(userId, paymentStatus);
		userDao.foodQuantityDecrease(id, quantity);

		return "paymentSuccess";
	}


	@GetMapping("/cartlist")
	public String showViewCart(Model model, HttpSession session) {
		int userId = (int) session.getAttribute("userid");
		int id = (int) session.getAttribute("foodId");

		List<Cart> cartItems = userDao.viewCart(userId);

		int hour = LocalTime.now().getHour();
		String mealTime;
		if (hour >= 6 && hour < 12) {
			mealTime = "Breakfast";
		} else if (hour >= 12 && hour < 17) {
			mealTime = "Lunch";
		} else {
			mealTime = "Dinner";
		}

		double overAllPrice = 0;
		for (Cart cartItem : cartItems) {
			overAllPrice += cartItem.getTotalPrice();

		}

		List<Cart> filteredCartItems = cartItems.stream()
				.filter(item -> item.getFoodSession().equalsIgnoreCase(mealTime)).collect(Collectors.toList());

		model.addAttribute("cart", filteredCartItems);
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("overAllPrice", overAllPrice);

		return "viewCart";
	}

	@PostMapping("/removeCartItem")
	public String removeCartItem(@RequestParam("foodId") int foodId) {
		userDao.removeCartItem(foodId);
		return "redirect:/cartlist";
	}

	/*
	 * 382 438
	 * 
	 * @PostMapping("/updateCartItemQuantity") public String
	 * updateCartItemQuantity(@RequestParam("foodId") int
	 * foodId, @RequestParam("quantity") int quantity, HttpSession session, Model
	 * model) { int userId = (int) session.getAttribute("userid"); double price = 0;
	 * double totalPrice = 0; System.out.println("food:"+foodId);
	 * userDao.updateCartItemQuantity(foodId, quantity);
	 * 
	 * 
	 * session.setAttribute("foodId", foodId);
	 * 
	 * session.setAttribute("quantity", quantity);
	 * 
	 * List<Cart> cartItems = userDao.viewCart(userId);
	 * 
	 * 
	 * 
	 * for (Cart cartItem : cartItems) { price = userDao.getFoodPrice(foodId);
	 * cartItem.setQuantity(quantity); System.out.println("Price :"+price);
	 * totalPrice=price * quantity; cartItem.setTotalPrice(totalPrice);
	 * System.out.println("total price -" + cartItem.getTotalPrice());
	 * System.out.println("quantity -" + cartItem.getQuantity());
	 * 
	 * } userDao.updateCartItemPrice(foodId, totalPrice);
	 * 
	 * int hour = LocalTime.now().getHour(); String mealTime; if (hour >= 6 && hour
	 * < 12) { mealTime = "Breakfast"; } else if (hour >= 12 && hour < 17) {
	 * mealTime = "Lunch"; } else { mealTime = "Dinner"; }
	 * 
	 * double overAllPrice = 0; for (Cart cartItem : cartItems) { overAllPrice +=
	 * cartItem.getTotalPrice();
	 * 
	 * 
	 * } List<Cart> filteredCartItems = cartItems.stream() .filter(item ->
	 * item.getFoodSession().equalsIgnoreCase(mealTime)).collect(Collectors.toList()
	 * );
	 * 
	 * System.out.println(filteredCartItems.get(0));
	 * System.out.println(filteredCartItems.get(1));
	 * System.out.println(filteredCartItems.get(2)); model.addAttribute("cart",
	 * filteredCartItems); model.addAttribute("cartItems", cartItems);
	 * model.addAttribute("overAllPrice", overAllPrice); return "viewCart"; }
	 * 
	 */
	@PostMapping("/updateCartItemQuantity")
	public String updateCartItemQuantity(@RequestParam("foodId") int foodId, @RequestParam("quantity") int quantity,
			HttpSession session, Model model) {
		int userId = (int) session.getAttribute("userid");

		userDao.updateCartItemQuantity(foodId, quantity);

		List<Cart> cartItems = userDao.viewCart(userId);

		
		double overAllPrice = 0;

		double updatedItemTotalPrice = 0;

		for (Cart cartItem : cartItems) {
			if (cartItem.getFoodId() == foodId) {
				cartItem.setQuantity(quantity);

				double pricePerItem = cartItem.getTotalPrice() / cartItem.getQuantity();
				cartItem.setTotalPrice(pricePerItem * quantity);

				updatedItemTotalPrice = cartItem.getTotalPrice();
			}

			overAllPrice += cartItem.getTotalPrice();
		}

		int hour = LocalTime.now().getHour();
		String mealTime;
		if (hour >= 6 && hour < 12) {
			mealTime = "Breakfast";
		} else if (hour >= 12 && hour < 17) {
			mealTime = "Lunch";
		} else {
			mealTime = "Dinner";
		}

		List<Cart> filteredCartItems = cartItems.stream()
				.filter(item -> item.getFoodSession().equalsIgnoreCase(mealTime)).collect(Collectors.toList());

		model.addAttribute("cart", filteredCartItems);
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("overAllPrice", overAllPrice);

		return "viewCart";
	}

	@PostMapping("/updateFoodPrice")
	public String updateFoodPrice(@RequestParam("foodId") int foodId, @RequestParam("newPrice") int newPrice) {
		userDao.updateFoodPrice(foodId, newPrice);
		return "redirect:/foodList";
	}

	@PostMapping("/updateFoodQuantity")
	public String updateFoodQuantity(@RequestParam("foodId") int foodId, @RequestParam("newQuantity") int newQuantity) {
		userDao.updateFoodQuantity(foodId, newQuantity);
		return "redirect:/foodList";
	}

	@PostMapping("/orderView")
	public String orderView(@RequestParam("hotelid") int hotelId, Model model) {
		List<Cart> cartItems = userDao.orderView(hotelId);

		double totalAmount = 0.0;
		for (Cart cartItem : cartItems) {
			totalAmount += cartItem.getTotalPrice();
		}

		model.addAttribute("cartItems", cartItems);
		model.addAttribute("totalAmount", totalAmount);

		/* return "orderview.jsp"; */
		return "orderview";
	}

	@PostMapping("/foodSearch")
	public String searchFood(@RequestParam("foodName") String foodName, Model model) {
		System.err.println("<----->");
		List<Food> foods = userDao.searchFood(foodName);
		for (Food food : foods) {
			String base64Image = Base64.getEncoder().encodeToString(food.getFoodImage());
			food.setBase64Image(base64Image);
		}

		model.addAttribute("foods", foods);
		return "addToCart";
	}

	@PostMapping("/updateOrders")
	public String updateOrders(@RequestParam("userId") int userId) {
		userDao.updateOrders(userId);
		return "orders-updated";
	}

	@PostMapping("/deleteFood")
	public String deleteFood(@RequestParam("foodId") int foodId) {
		System.out.println(foodId);
		userDao.deleteFood(foodId);
		return "redirect:/foodList";
	}

	@GetMapping("/foodSearchh")
	public String searchFoodMenu(@RequestParam("foodName") String foodName, Model model) {
		List<Food> foods = userDao.searchFoodByName(foodName);
		model.addAttribute("foodList", foods);
		return "/menu";
	}

	@PostMapping("/updateDeliveryStatus")
	public String updateStatus(@RequestParam("hotelid") int hotelId, @RequestParam("delivered") String delivered,
			@RequestParam("foodName") String foodName) {
		Cart cartItem = new Cart();
		cartItem.setHotelId(hotelId);
		cartItem.setDeliveryStatus(delivered);
		cartItem.setFoodName(foodName);
		/*
		 * System.out.println("<--->" + cartItem.getHotelId());
		 * System.err.println("<--->" + cartItem.getDeliveryStatus());
		 */ System.out.println(cartItem.getFoodName());
		userDao.updateDeliveryStatus(cartItem);
		return "hotelAdminDashboard";
	}

	@GetMapping("/deliveredOrders")
	public String showDeliveredOrders(Model model) {
		List<Cart> deliveredOrders = userDao.getDeliveredOrders();
		System.out.println(deliveredOrders);
		model.addAttribute("deliveredOrders", deliveredOrders);
		return "deliveredOrders";
	}

}
