package musiclibrary.beans;

import java.util.List;

import javax.persistence.Embeddable;
//import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @authors Viktoriia Denys, Kaitlyn Briggs & Logan Kennebeck - vdenys, krbriggs & ljkennebeck1
 * CIS175-Fall 2022
 * Nov 16, 2022
 */
@Entity
@Embeddable
@Table(name="playlist")
public class Playlist {
	@Id
	@GeneratedValue
	private int id;
	private String playlistName;
	@Autowired
	@OneToMany(targetEntity=Song.class)
	private List<Song> songs;
	
	// Constructors
	public Playlist() {
		super();
	}
	
	public Playlist(int id, String playlistName, List<Song> songs) {
		super();
		this.id = id;
		this.playlistName = playlistName;
		this.songs = songs;
	}
	
	public Playlist(String playlistName, List<Song> songs) {
		super();
		this.playlistName = playlistName;
		this.songs = songs;
	}
	
	public Playlist(String playlistName) {
		super();
		this.playlistName = playlistName;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}
	
	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	// toString
	@Override
	public String toString() {
		return "Playlist [id=" + id + ", playlistName=" + playlistName + ", songs=" + songs + "]";
	}
}
