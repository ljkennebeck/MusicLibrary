package musiclibrary.beans;

import java.util.ArrayList;

import javax.persistence.Embeddable;
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
	private long id;
	private String playlistName;
	private ArrayList<Song> songs;
	
	// Constructors
	public Playlist() {
		super();
	}
	
	public Playlist(int id, String playlistName, ArrayList<Song> songs) {
		super();
		this.id = id;
		this.playlistName = playlistName;
		this.songs = songs;
	}
	
	public Playlist(String playlistName, ArrayList<Song> songs) {
		super();
		this.playlistName = playlistName;
		this.songs = songs;
	}
	
	public Playlist(String playlistName) {
		super();
		this.playlistName = playlistName;
	}

	// Getters and Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}
	
	public ArrayList<Song> getSongs() {
		return songs;
	}

	public void setSongs(ArrayList<Song> songs) {
		this.songs = songs;
	}

	// toString
	@Override
	public String toString() {
		return "Playlist [id=" + id + ", playlistName=" + playlistName + ", songs=" + songs + "]";
	}
}
