/**
 * @author Kaitlyn Briggs - krbriggs
 * CIS175 - Fall 2022
 * Dec 8, 2022
 */
package musiclibrary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import musiclibrary.beans.Review;
import musiclibrary.beans.Song;
import musiclibrary.beans.User;
import musiclibrary.repository.ArtistRepository;
import musiclibrary.repository.GenreRepository;
import musiclibrary.repository.PlaylistRepository;
import musiclibrary.repository.ReviewRepository;
import musiclibrary.repository.SongRepository;
import musiclibrary.repository.UserRepository;

/**
 * @authors Viktoriia Denys, Kaitlyn Briggs & Logan Kennebeck - vdenys, krbriggs & ljkennebeck1
 * CIS175-Fall 2022
 * Nov 16, 2022
 */
@Controller
public class UserController {
	@Autowired
	SongRepository repo;
	
	@Autowired
	UserRepository repoU;
	
	@Autowired
	PlaylistRepository repoP;
	
	@Autowired
	ArtistRepository repoA;
	
	@Autowired
	GenreRepository repoG;
	
	@Autowired
	ReviewRepository repoR;
	
	@Autowired
	SongController controller;
	
	//------------------------------------------------------------------------------------------//
	//------------------------------------------User Area---------------------------------------//
	//------------------------------------------------------------------------------------------//
	
	//each of the above and future mappings needs a userInfo param to keep track of the logged on user
	
	@Autowired
	UserRepository uRepo;
	
	@GetMapping("/viewProfile")
	public String viewProfile(@RequestParam("userInfo") String username, Model model) {
		model.addAttribute("userInfo", username);
		return "profile";
	}
	
	@GetMapping("/redirectSignUp")
	public String redirectSignUp(Model model) {
		return "signUp";
	}
	
	@GetMapping({"/", "/redirectSignIn"})
	public String redirectSignIn(Model model) {
		return "signIn";
	}
	
	@GetMapping("/signIn")
	public String signIn(@RequestParam("username") String username, @RequestParam("password")String password, Model model) {
		String errorMessage = "Incorrect Username or Password";
		List<User> foundUser = uRepo.findByUsernameAndPassword(username, password);
		if(foundUser.isEmpty()) {
			model.addAttribute("errorMessage", errorMessage);
			return "signIn";
		}
		else {
			User u = new User(username, password);
			model.addAttribute("userInfo", u.getUsername());
			return this.controller.viewAllSongs(username, model);
		}
	}
	
	@GetMapping("/addUser")
	public String addUser(@RequestParam("username") String username, @RequestParam("password")String password, @RequestParam("devCode") String devCode, Model model) {
		String errorMessage = "Username Taken";
		List<User> foundUser = uRepo.findByUsername(username);
		if(!foundUser.isEmpty()) {
			model.addAttribute("errorMessage", errorMessage);
			return "signUp";
		}
		else {
			User newUser = new User(username, password);
			if(devCode.equals("horseshoe")) {//developer code on sign up == horseshoe
				newUser.setDev(true);
			}
			else {
				newUser.setDev(false);
			}
			uRepo.save(newUser);
			model.addAttribute("userInfo", newUser.getUsername());
			return this.controller.viewAllSongs(username, model);
		}
	}
	
	@GetMapping("/addReview")
	public String addReview(@RequestParam("userInfo") String username, Model model) {
		Review r = new Review();
		model.addAttribute("newReview", r);
		model.addAttribute("userInfo", username);
		return "submitReview";
	}
	
	@PostMapping("/postReview/{id}")
	public String postReview(@RequestParam("userInfo") String username, Review r, Model model) {
		r.setUser(username);
		repoR.save(r);
		model.addAttribute("userInfo", username);
		model.addAttribute("message", "Thank you for your input");
		return controller.viewAllSongs(username, model);
	}
	
	@GetMapping("/viewReviews")
	public String viewReviews(@RequestParam("userInfo") String username, Model model) {
		List<User> user = repoU.findByUsername(username);
		if(user.get(0).isDev() == false) {
			model.addAttribute("message", "You do not have access to this page");
			return controller.viewAllSongs(username, model);
		}
		if(repoR.findAll().isEmpty()) {
			model.addAttribute("message", "No Available Reviews");
			return controller.viewAllSongs(username, model);
		}
		model.addAttribute("reviews", repoR.findAll());
		model.addAttribute("userInfo", username);
		return "devView";
	}
	
	@GetMapping("/deleteReview/{id}/{userInfo}")
	public String deleteReview(@PathVariable("userInfo") String username, @PathVariable("id") long id, Model model) {
		Review r = repoR.findById(id).orElse(null);
		repoR.delete(r);
		model.addAttribute("userInfo", username);
		return viewReviews(username, model);
	}


}
