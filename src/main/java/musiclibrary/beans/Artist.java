/**
 * @author Kaitlyn Briggs - krbriggs
 * CIS175 - Fall 2022
 * Nov 15, 2022
 */
package musiclibrary.beans;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="artist")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Artist {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private long id;
	@Column(name="NAME")
	private String artistName;
	@Column(name="BIO")
	private String bio;
	@OneToMany(targetEntity=Song.class, mappedBy = "artist")
	private List<Song> songs;		
	
	public Artist(String inputArtistName) {
		this.artistName = inputArtistName;
	}

}
