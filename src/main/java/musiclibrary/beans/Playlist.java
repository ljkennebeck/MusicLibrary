package musiclibrary.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @authors Viktoriia Denys, Kaitlyn Briggs & Logan Kennebeck - vdenys, krbriggs & ljkennebeck1
 * CIS175-Fall 2022
 * Nov 16, 2022
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Embeddable
@Table(name="playlist")
public class Playlist {
	@Id
	@GeneratedValue
	private long id;
	private String user;
	private String playlistName;
	private ArrayList<Song> songs;
	
	public Playlist() {
		super();
		// TODO Auto-generated constructor stub
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

	/**
	 * @param s
	 */
	public void addToPlaylist(Song s) {
		songs.add(s);
		
	}

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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	

	
	
	
	

}
