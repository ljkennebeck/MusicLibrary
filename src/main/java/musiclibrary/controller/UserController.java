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
import org.springframework.web.bind.annotation.RequestParam;

import musiclibrary.beans.User;
import musiclibrary.repository.ArtistRepository;
import musiclibrary.repository.GenreRepository;
import musiclibrary.repository.PlaylistRepository;
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
	PlaylistRepository repoP;
	
	@Autowired
	ArtistRepository repoA;
	
	@Autowired
	GenreRepository repoG;
	
	
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
			return "viewAllSongs";
		}
	}
	
	@GetMapping("/addUser")
	public String addUser(@RequestParam("username") String username, @RequestParam("password")String password, Model model) {
		String errorMessage = "Username Taken";
		List<User> foundUser = uRepo.findByUsername(username);
		if(!foundUser.isEmpty()) {
			model.addAttribute("errorMessage", errorMessage);
			return "signUp";
		}
		else {
			User newUser = new User(username, password);
			uRepo.save(newUser);
			model.addAttribute("userInfo", newUser.getUsername());
			return "viewAllSongs";
		}
	}


}
