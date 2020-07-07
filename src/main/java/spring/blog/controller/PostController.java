package spring.blog.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import spring.blog.model.Post;
import spring.blog.model.User;
import spring.blog.service.NotificationService;
import spring.blog.service.PostService;
import spring.blog.service.UserService;
import java.util.List;

@Controller
public class PostController {
	@Autowired
	private PostService postService;
	@Autowired
	private UserService userService;
	@Autowired
	private NotificationService notifyService;

	// Get Home file
	@RequestMapping( value= { "/", "/home" } )
	public String index(Model model) {
		// Get last 3 post
		List<Post> latest3Posts = this.postService.findLatest3();

		// Define variables and pass to view model
		model.addAttribute("latest3Posts", latest3Posts);

		// Return the view model index.html
		return "index"; 
	}
	
	// Get a Post
	@RequestMapping("/posts/view/{id}")
	public String getPostById(@PathVariable("id") Long id, Model model) {
		// Get the post
		Post post = this.postService.findById(id);
		if ( post == null ) {
			notifyService.addErrorMessage("Cannot find post #" + id);
			return "redirect:/";
		}

		// Define variables and pass to view model
		model.addAttribute("post", post);

		// Return the view model view.html
		return "posts/view";		
	}

	// Get a List of Posts in the DB & Paginate the data
	@RequestMapping("/posts")
	public String post(Model model, @PageableDefault(sort = {"id"}, value = 4) Pageable pageable) {
		// Get the content of the table
		Page<Post> posts = this.postService.findAll(pageable);

		// Define variables and pass to view model
		model.addAttribute("posts", posts);

		// Return the view model post.html
		return "posts/post";
	}
	
	// Create a Post
	@RequestMapping("/posts/create")
	public ModelAndView create() {
		ModelAndView modelAndView = new ModelAndView();
		Post post = new Post();
		modelAndView.addObject(post);
		modelAndView.setViewName("posts/create");
		return modelAndView;		
	}
	
	// Display a Form to Create a Post
	@RequestMapping(value = "/posts/create", method = RequestMethod.POST)
	public ModelAndView create(@Valid Post post, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName("posts/create");

		// Perform validation
		if ( post.getTitle().isEmpty() ) {
			bindingResult.rejectValue("title", "error.post", "Title cannot be empty");
		}
		if ( post.getBody().isEmpty() ) {
			bindingResult.rejectValue("body", "error.post", "Content cannot be empty");
		}

		// Get author
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = this.userService.findByUsername(auth.getName());
		if ( user==null ) {
			bindingResult.rejectValue("author", "error.post", "Author cannot be null");
		}
		if ( !bindingResult.hasErrors() ) {
			post.setAuthor(user);
			this.postService.create(post);
			modelAndView.addObject("successMessage", "Post has been created");
			modelAndView.addObject("post", new Post());
		}
		return modelAndView;		
	}

	// Display a Form to Update a Post
	@RequestMapping( "/posts/edit/{id}" )
	public String edit(@PathVariable("id") Long id, Model model) {
		// Get the post
		Post post = this.postService.findById(id);
		if ( post == null ) {
			notifyService.addErrorMessage("Cannot find post #" + id);
			return "redirect:/posts/";
		}

		// Define variables and pass to view model
		model.addAttribute("post", post);

		// Return the view model edit.html
		return "posts/edit";
	}
	
	// Update a Post
	@RequestMapping(value = "/posts/edit", method = RequestMethod.POST )
	public ModelAndView edit(@Valid Post post, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName("posts/edit");

		// Perform validation
		if ( post.getTitle().isEmpty() ) {
			bindingResult.rejectValue("title", "error.post", "Title cannot be empty");
		}
		if ( post.getBody().isEmpty() ) {
			bindingResult.rejectValue("body", "error.post", "Content cannot be empty");
		}

		// Get author		
		User user = this.userService.findById(post.getAuthor().getId());
		if ( user==null ) {
			bindingResult.rejectValue("author", "error.post", "Author cannot be null");
		}
		if ( !bindingResult.hasErrors() ) {
			post.setAuthor(user);
			this.postService.create(post);
			modelAndView.addObject("successMessage", "Post has been updated");
			modelAndView.addObject("post", post);
		}
		return modelAndView;
	}
	
	// Delete a Post
	@RequestMapping("/posts/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		// Get the post
		Post post = this.postService.findById(id);
		if ( post==null ) {
			notifyService.addErrorMessage("Cannot find post #" + id);			
		} else {
			this.postService.deleteById(id);
		}
		return "redirect:/posts/";
	}

}
