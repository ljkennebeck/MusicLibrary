package musiclibrary.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @authors Viktoriia Denys, Kaitlyn Briggs & Logan Kennebeck - vdenys, krbriggs & ljkennebeck1
 * CIS175-Fall 2022
 * Nov 16, 2022
 */
@Entity
@Table(name="songs")
public class Song {
	
	// Variables
	@Id
	@GeneratedValue
	@Column(name="ID")
	private long id;
	@Column(name="TITLE")
	private String title;
	@Column(name="ARTIST")
	private String artist;
	
	// Constructors
	public Song() {
		super();
	}
	
	public Song(String title, String artist) {
		super();
		this.title = title;
		this.artist = artist;
	}
	
	// Getters and Setters
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}

	// Helper Methods
	public String returnSongDetails( ) {
		return this.title + ", by " + this.artist;
	}

}