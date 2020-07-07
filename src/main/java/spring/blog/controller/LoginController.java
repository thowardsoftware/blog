package spring.blog.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import spring.blog.form.LoginForm;
import spring.blog.model.User;
import spring.blog.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	// Display login form
	@RequestMapping("/users/login")
	public String login(LoginForm loginForm){
		// User doesn't need to re-enter credentials
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if ( (auth instanceof AnonymousAuthenticationToken) ) {
			return "users/login";
		} else {
			return "redirect:/";
		}
	}

	// Display registration form
	@RequestMapping("/users/register")
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject(user);
		modelAndView.setViewName("users/register");
		return modelAndView;
	}

	// Register user
	@RequestMapping(value = "users/register", method = RequestMethod.POST)
	public ModelAndView registration(@Valid User user, BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();
		User userExists = this.userService.findByUsername(user.getUsername());
		modelAndView.setViewName("users/register");
		if( userExists != null ){
			bindingResult.rejectValue("username", "error.user", "User exists");
		}
		if( !bindingResult.hasErrors() ){
			this.userService.create(user);
			modelAndView.addObject("successMessage", "User has been created");
			modelAndView.addObject("user", new User());
		}
		return modelAndView;
	}

}
