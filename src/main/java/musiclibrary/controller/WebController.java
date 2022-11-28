package musiclibrary.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import musiclibrary.beans.Genre;
import musiclibrary.beans.Playlist;
import musiclibrary.beans.Song;
import musiclibrary.beans.User;
import musiclibrary.repository.PlaylistRepository;
import musiclibrary.repository.SongRepository;
import musiclibrary.repository.UserRepository;

/**
 * @authors Viktoriia Denys, Kaitlyn Briggs & Logan Kennebeck - vdenys, krbriggs & ljkennebeck1
 * CIS175-Fall 2022
 * Nov 16, 2022
 */
@Controller
public class WebController {
	@Autowired
	SongRepository repo;
	
	@Autowired
	PlaylistRepository repoP;
	
	@GetMapping("/viewAllSongs") //each of these mappings needs a userInfo param to keep track of the logged on user
	public String viewAllSongs(@RequestParam("userInfo") String username, Model model) {
		if(repo.findAll().isEmpty()) {
			return addNewSong(username, model);
		}
		model.addAttribute("songs", repo.findAll());
		model.addAttribute("userInfo", username);
		return "viewSongs";
	}
	
	@GetMapping("/addSong")
	public String addNewSong(@RequestParam("userInfo") String username, Model model) {
		Song s = new Song();
		model.addAttribute("newSong", s);
		model.addAttribute("userInfo", username);
		return "inputSong";
	}
	
	@GetMapping("/editSong/{id}/{userInfo}")
	public String showUpdateSong(@PathVariable("userInfo") String username, @PathVariable("id") long id, Model model) {
		Song s = repo.findById(id).orElse(null);
		model.addAttribute("newSong", s);
		model.addAttribute("userInfo", username);
		return "inputSong";
	}
	
	@PostMapping("/updateSong/{id}")
	public String updateSong(@RequestParam("userInfo") String username, Song s, Model model) {
		repo.save(s);
		model.addAttribute("userInfo", username);
		return viewAllSongs(username, model);
	}
	
	@GetMapping("/deleteSong/{id}/{userInfo}")
	public String deleteSong(@PathVariable("userInfo") String username, @PathVariable("id") long id, Model model) {
		Song s = repo.findById(id).orElse(null);
		repo.delete(s);
		model.addAttribute("userInfo", username);
		return viewAllSongs(username, model);
	}
	
	@GetMapping("/searchForSong")
	public String searchForSong(@RequestParam("userInfo") String username, @RequestParam(name = "title") String title, @RequestParam(name = "artist") String artist, @RequestParam(name = "genre") String genre, Model model) {
		return searchSong(username, title, artist, genre, model);
	}
	
	public String searchSong(String username, String title, String artist, String genre, Model model) {
		String path = "foundSongs";
		List<Song> songs = new ArrayList<Song>();
		if(title.equals("") && artist.equals("") && genre.equals("")) {
			return viewAllSongs(username, model);
		}
		else if(title.equals("") && artist.equals("")) {
			songs = repo.findByGenre(genre);
			model.addAttribute("foundSongs", songs);
		}
		else if(title.equals("") && genre.equals("")) {
			songs = repo.findByArtist(artist);
			model.addAttribute("foundSongs", songs);
		}
		else if(artist.equals("") && genre.equals("")) {
			songs = repo.findByTitle(title);
			model.addAttribute("foundSongs", songs);
		}
		else if(title.equals("")) {
			songs = repo.findByGenreAndArtist(genre, artist);
			model.addAttribute("foundSongs", songs);
		}
		else if(artist.equals("")) {
			songs = repo.findByTitleAndGenre(title, genre);
			model.addAttribute("foundSongs", songs);
		}
		else if(genre.equals("")) {
			songs = repo.findByTitleAndArtist(title, artist);
			model.addAttribute("foundSongs", songs);
		}
		else {
			songs = repo.findByTitleAndArtistAndGenre(title, artist, genre);
			model.addAttribute("foundSongs", songs);
		}
		model.addAttribute("userInfo", username);
		return path;
	}
	
	@GetMapping("/viewAllPlaylists") 
	public String viewAllPlaylists(@RequestParam("userInfo") String username, Model model) {
		if(repoP.findAll().isEmpty()) {
			return addNewPlaylist(username, model);
		}
		model.addAttribute("playlists", repoP.findAll());
		model.addAttribute("userInfo", username);
		return "profile";
	}
	
	@GetMapping("/addPlaylist")
	public String addNewPlaylist(@RequestParam("userInfo") String username, Model model) {
		Playlist p = new Playlist();
		model.addAttribute("newPlaylist", p);
		model.addAttribute("userInfo", username);
		return "newPlaylist";
	}
	
	@GetMapping("/editPlaylist/{id}/{userInfo}")
	public String showUpdatePlaylist(@PathVariable("userInfo") String username, @PathVariable("id") long id, Model model) {
		Playlist p = repoP.findById(id).orElse(null);
		model.addAttribute("newPlaylist", p);
		model.addAttribute("userInfo", username);
		return "newPlaylist";
	}
	
	@PostMapping("/updatePlaylist/{id}")
	public String updatePlaylist(@RequestParam("userInfo") String username, Playlist p, Model model) {
		repoP.save(p);
		model.addAttribute("userInfo", username);
		return viewAllPlaylists(username, model);
	}
	
	@GetMapping("/deletePlaylist/{id}/{userInfo}")
	public String deletePlaylist(@PathVariable("userInfo") String username, @PathVariable("id") long id, Model model) {
		Playlist p = repoP.findById(id).orElse(null);
		repoP.delete(p);
		model.addAttribute("userInfo", username);
		return viewAllPlaylists(username, model);
	}
	
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
			return viewAllSongs(username, model);
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
			return viewAllSongs(username, model);
		}
	}


}
