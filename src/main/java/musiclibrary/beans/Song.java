package musiclibrary.beans;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Embeddable
@Table(name="songs")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Song {
	
	// Variables
	@Id
	@GeneratedValue
	@Column(name="ID")
	private long id;
	@Column(name="TITLE")
	private String title;
	@Autowired
	@ManyToOne
    @JoinColumn(name = "artist_artistName")
	private Artist artist;
	@Autowired
	@ManyToOne
    @JoinColumn(name = "genre_genreName")
	private Genre genre;
	
	


}