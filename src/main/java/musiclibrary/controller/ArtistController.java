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

import musiclibrary.beans.Artist;
import musiclibrary.repository.ArtistRepository;
import musiclibrary.repository.SongRepository;


@Controller
public class ArtistController {
	
	@Autowired
	ArtistRepository repoA;
	
	@Autowired
	SongRepository repo;
	
	

	//Artist
	
	@GetMapping("/viewAllArtists") 
	public String viewAllArtists(@RequestParam("userInfo") String username, Model model) {
		if(repoA.findAll().isEmpty()) {
			return addNewArtist(username, model);
		}
		model.addAttribute("artists", repoA.findAll());
		model.addAttribute("userInfo", username);
		return "viewAllArtists";
	}
	
	@GetMapping("/newArtist")
	public String addNewArtist(@RequestParam("userInfo") String username, Model model) {
		Artist a = new Artist();
		model.addAttribute("newArtist", a);
		model.addAttribute("userInfo", username);
		return "newArtist";
	}
	
	@GetMapping("/editArtist/{id}/{userInfo}")
	public String showUpdateArtist(@PathVariable("userInfo") String username, @PathVariable("id") long id, Model model) {
		Artist a = repoA.findById(id).orElse(null);
		model.addAttribute("newArtist", a);
		model.addAttribute("userInfo", username);
		return "newArtist";
	}
	
	@PostMapping("/updateArtist/{id}")
	public String updateArtist(@RequestParam("userInfo") String username, Artist a, Model model) {
		repoA.save(a);
		model.addAttribute("userInfo", username);
		return viewAllArtists(username, model);
	}
	
	@GetMapping("/deleteArtist/{id}/{userInfo}")
	public String deleteArtist(@PathVariable("userInfo") String username, @PathVariable("id") long id, Model model) {
		if(repoA.findById(id) == null) {
			return "viewAllArtists";
		}
		Artist a = repoA.findById(id).orElse(null);
		repoA.delete(a);
		model.addAttribute("userInfo", username);
		return viewAllArtists(username, model);
	}
	
	@GetMapping("/viewArtist/{id}/{userInfo}") 
	public String viewArtist(@PathVariable("userInfo") String username, @PathVariable("id") long id, Model model) {
		Artist a = repoA.findById(id).orElse(null);
		a.setSongs(repo.findByArtist(a.getArtistName()));
		model.addAttribute("foundSongs", a.getSongs());
		model.addAttribute("viewArtist", a);
		model.addAttribute("userInfo", username);
		return "viewArtist";
	}
	
	@GetMapping("/searchForArtist")
	public String searchForArtist(@RequestParam("userInfo") String username, @RequestParam(name = "artistName") String artistName, Model model) {
		return searchArtist(username, artistName, model);
	}
	
	public String searchArtist(String username, String artistName, Model model) {
		String path = "foundArtists";
		List<Artist> artists = new ArrayList<Artist>();
		if(artistName.equals("")) {
			return viewAllArtists(username, model);
		}
		else {
			artists = repoA.findByArtistName(artistName);
			model.addAttribute("foundArtists", artists);
		}
		model.addAttribute("userInfo", username);
		return path;
	}


}
