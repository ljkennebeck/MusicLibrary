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
import musiclibrary.beans.Playlist;
import musiclibrary.beans.Review;
import musiclibrary.beans.Song;
import musiclibrary.repository.ArtistRepository;
import musiclibrary.repository.GenreRepository;
import musiclibrary.repository.PlaylistRepository;
import musiclibrary.repository.ReviewRepository;
import musiclibrary.repository.SongRepository;

/**
 * @authors Viktoriia Denys, Kaitlyn Briggs & Logan Kennebeck - vdenys, krbriggs & ljkennebeck1
 * CIS175-Fall 2022
 * Nov 16, 2022
 */
@Controller
public class SongController {
	@Autowired
	SongRepository repo;
	
	@Autowired
	PlaylistRepository repoP;
	
	@Autowired
	ArtistRepository repoA;
	
	@Autowired
	GenreRepository repoG;
	
	//Songs
	
	@GetMapping("/viewAllSongs") //each of these mappings needs a userInfo param to keep track of the logged on user
	public String viewAllSongs(@RequestParam("userInfo") String username, Model model) {
		if(repo.findAll().isEmpty()) {
			return addNewSong(username, model);
		}
		model.addAttribute("songs", repo.findAll());
		model.addAttribute("userInfo", username);
		model.addAttribute("playlists", repoP.findAll());
		return "viewSongs";
	}
	
	@GetMapping("/addSong")
	public String addNewSong(@RequestParam("userInfo") String username, Model model) {
		Song s = new Song();
		model.addAttribute("newSong", s);
		model.addAttribute("userInfo", username);
		model.addAttribute("artist", repoA.findAll());
		model.addAttribute("genre", repoG.findAll());
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
	public String updateSong(@RequestParam("userInfo") String username, @RequestParam("artist") String artist, @RequestParam("genre") String genre, Song s, Model model) {
		if(repoA.findByArtistName(artist).isEmpty()) {
			model.addAttribute("message", "Entered Artist does not exist");
			return viewAllSongs(username, model);
		}
		if(repoG.findByGenreName(genre).isEmpty()) {
			model.addAttribute("message", "Entered Genre does not exist");
			return viewAllSongs(username, model);
		}
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
	
	@GetMapping("/addToPlaylist/{id}/{userInfo}/{playlistId}")
	public String addSongToPlaylist(@PathVariable("userInfo") String username, @PathVariable("id") long id, @PathVariable(name = "playlistId") long playlistId, Model model) {
		Playlist p = repoP.findById(playlistId).orElse(null);
		Song s = repo.findById(id).orElse(null);
		ArrayList<Song> playlistSongs = new ArrayList<Song>();
		if(p.getSongs() == null) {
			playlistSongs.add(s);
			p.setSongs(playlistSongs);
			repoP.save(p);
			return viewAllSongs(username, model);
		}else {
			p.addToPlaylist(s);
			repoP.save(p);
			return viewAllSongs(username, model);
		}
	}
	
	@GetMapping("/selectPlaylist/{id}/{userInfo}") 
	public String SelectPlaylist(@PathVariable("userInfo") String username, @PathVariable("id") long id, Model model) {
		if(repoP.findByUser(username).isEmpty()) {
			return viewAllSongs(username, model);
		}
		model.addAttribute("playlists", repoP.findByUser(username));
		model.addAttribute("userInfo", username);
		model.addAttribute("id", id);
		return "playlistSelect";
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

}
