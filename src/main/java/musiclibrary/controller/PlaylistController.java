/**
 * @author Kaitlyn Briggs - krbriggs
 * CIS175 - Fall 2022
 * Dec 8, 2022
 */
package musiclibrary.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import musiclibrary.beans.Playlist;
import musiclibrary.beans.Song;
import musiclibrary.repository.PlaylistRepository;
import musiclibrary.repository.SongRepository;

/**
 * @authors Viktoriia Denys, Kaitlyn Briggs & Logan Kennebeck - vdenys, krbriggs & ljkennebeck1
 * CIS175-Fall 2022
 * Nov 16, 2022
 */
@Controller
public class PlaylistController {
	@Autowired
	SongRepository repo;
	
	@Autowired
	PlaylistRepository repoP;

	
	//Playlists
	
	@GetMapping("/viewAllPlaylists") 
	public String viewAllPlaylists(@RequestParam("userInfo") String username, Model model) {
		if(repoP.findByUser(username).isEmpty()) {
			return addNewPlaylist(username, model);
		}
		model.addAttribute("playlists", repoP.findByUser(username));
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
		p.setUser(username);
		repoP.save(p);
		model.addAttribute("userInfo", username);
		return viewAllPlaylists(username, model);
	}
	
	@GetMapping("/deletePlaylist/{id}/{userInfo}")
	public String deletePlaylist(@PathVariable("userInfo") String username, @PathVariable("id") long id, Model model) {
		if(repoP.findById(id) == null) {
			return "profile";
		}
		Playlist p = repoP.findById(id).orElse(null);
		repoP.delete(p);
		model.addAttribute("userInfo", username);
		return viewAllPlaylists(username, model);
	}
	
	@GetMapping("/viewPlaylist/{id}/{userInfo}") 
	public String viewPlaylist(@PathVariable("userInfo") String username, @PathVariable("id") long id, Model model) {
		Playlist p = repoP.findById(id).orElse(null);
		ArrayList<Song> songs = p.getSongs();
		model.addAttribute("viewPlaylist", p);
		model.addAttribute("playlistSongs", songs);
		model.addAttribute("userInfo", username);
		return "viewPlaylist";
	}
}
