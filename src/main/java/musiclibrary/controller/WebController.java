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
import musiclibrary.beans.Song;
import musiclibrary.repository.SongRepository;

/**
 * @authors Viktoriia Denys, Kaitlyn Briggs & Logan Kennebeck - vdenys, krbriggs & ljkennebeck1
 * CIS175-Fall 2022
 * Nov 16, 2022
 */
@Controller
public class WebController {
	@Autowired
	SongRepository repo;
	
	@GetMapping({"/", "viewAllSongs"})
	public String viewAllSongs(Model model) {
		if(repo.findAll().isEmpty()) {
			return addNewSong(model);
		}
		model.addAttribute("songs", repo.findAll());	
		return "viewSongs";
	}
	
	@GetMapping("/addSong")
	public String addNewSong(Model model) {
		Song s = new Song();
		model.addAttribute("newSong", s);
		return "inputSong";
	}
	
	@GetMapping("/editSong/{id}")
	public String showUpdateSong(@PathVariable("id") long id, Model model) {
		Song s = repo.findById(id).orElse(null);
		model.addAttribute("newSong", s);
		return "inputSong";
	}
	
	@PostMapping("/updateSong/{id}")
	public String updateSong(Song s, Model model) {
		repo.save(s);
		return viewAllSongs(model);
	}
	
	@GetMapping("/deleteSong/{id}")
	public String deleteSong(@PathVariable("id") long id, Model model) {
		Song s = repo.findById(id).orElse(null);
		repo.delete(s);
		return viewAllSongs(model);
	}
	
	@GetMapping("/searchForSong")
	public String searchForSong(@RequestParam(name = "title") String title, @RequestParam(name = "artist") String artist, @RequestParam(name = "genre") String genre, Model model) {
		return searchSong(title, artist, genre, model);
	}
	
	public String searchSong(String title, String artist, String genre, Model model) {
		String path = "foundSongs";
		List<Song> songs = new ArrayList<Song>();
		if(title.equals("") && artist.equals("") && genre.equals("")) {
			return viewAllSongs(model);
		}
		else if(title.equals("") && artist.equals("")) {
			songs = repo.findByGenre(genre);
			model.addAttribute("foundSongs", songs);
		}
		else if(title.equals("")) {
			songs = repo.findByArtist(artist);
			model.addAttribute("foundSongs", songs);
		}
		else if(artist.equals("")) {
			songs = repo.findByTitle(title);
			model.addAttribute("foundSongs", songs);
		}
		else {
			songs = repo.findByTitleAndArtist(title, artist);
			model.addAttribute("foundSongs", songs);
		}
		return path;
	}
		


}
