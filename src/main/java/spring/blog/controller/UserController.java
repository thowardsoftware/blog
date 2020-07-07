package spring.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.blog.model.User;
import spring.blog.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// Get a list of all Users in the DB & Paginate the data
	@RequestMapping("/users")
	public String index(Model model, @PageableDefault(sort = {"username"}, value = 5) Pageable pageable){		
		// Get the content of the table
		Page<User> users = this.userService.findAll(pageable);
		
		// Define variables and pass to view model
		model.addAttribute("users", users);
		
		// Return the view model user.html
		return "users/user";
	}

}
