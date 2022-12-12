/**
 * @author Kaitlyn Briggs - krbriggs
 * CIS175 - Fall 2022
 * Dec 8, 2022
 */
package musiclibrary.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import musiclibrary.beans.Genre;
import musiclibrary.repository.ArtistRepository;
import musiclibrary.repository.GenreRepository;
import musiclibrary.repository.SongRepository;

/**
 * @authors Viktoriia Denys, Kaitlyn Briggs & Logan Kennebeck - vdenys, krbriggs & ljkennebeck1
 * CIS175-Fall 2022
 * Nov 16, 2022
 */
@Controller
public class GenreController {
	@Autowired
	SongRepository repo;
	

	@Autowired
	ArtistRepository repoA;
	
	@Autowired
	GenreRepository repoG;
	

	//Genre
	@GetMapping("/viewGenre/{id}/{userInfo}") 
	public String viewGenre(@PathVariable("userInfo") String username, @PathVariable("id") long id, Model model) {
		Genre g = repoG.findById(id).orElse(null);
		g.setSongs(repo.findByGenre(g.getGenreName()));
		model.addAttribute("foundSongs", g.getSongs());
		model.addAttribute("viewGenre", g);
		model.addAttribute("userInfo", username);
		return "viewGenre";
	}
	
	@GetMapping("/viewAllGenres") 
	public String viewAllGenres(@RequestParam("userInfo") String username, Model model) {
		if(repoG.findAll().isEmpty()) {
			return addNewGenre(username, model);
		}
		model.addAttribute("genres", repoG.findAll());
		model.addAttribute("userInfo", username);
		return "viewAllGenres";
	}
	
	@GetMapping("/newGenre")
	public String addNewGenre(@RequestParam("userInfo") String username, Model model) {
		Genre g = new Genre();
		model.addAttribute("newGenre", g);
		model.addAttribute("userInfo", username);
		return "newGenre";
	}
	
	@GetMapping("/editGenre/{id}/{userInfo}")
	public String showUpdateGenre(@PathVariable("userInfo") String username, @PathVariable("id") long id, Model model) {
		Genre g = repoG.findById(id).orElse(null);
		model.addAttribute("newGenre", g);
		model.addAttribute("userInfo", username);
		return "newGenre";
	}
	
	@PostMapping("/updateGenre/{id}")
	public String updateArtist(@RequestParam("userInfo") String username, Genre g, Model model) {
		repoG.save(g);
		model.addAttribute("userInfo", username);
		return viewAllGenres(username, model);
	}
	
	@GetMapping("/deleteGenre/{id}/{userInfo}")
	public String deleteGenre(@PathVariable("userInfo") String username, @PathVariable("id") long id, Model model) {
		if(repoG.findById(id) == null) {
			return "viewAllGenres";
		}
		Genre g = repoG.findById(id).orElse(null);
		repoG.delete(g);
		model.addAttribute("userInfo", username);
		return viewAllGenres(username, model);
	}

	
	@GetMapping("/searchForGenre")
	public String searchForGenre(@RequestParam("userInfo") String username, @RequestParam(name = "genreName") String genreName, Model model) {
		return searchGenre(username, genreName, model);
	}
	
	public String searchGenre(String username, String genreName, Model model) {
		String path = "foundGenres";
		List<Genre> genres = new ArrayList<Genre>();
		if(genreName.equals("")) {
			return viewAllGenres(username, model);
		}
		else {
			genres = repoG.findByGenreName(genreName);
			model.addAttribute("foundGenres", genres);
		}
		model.addAttribute("userInfo", username);
		return path;
	}

}
