/**
 * @author Kaitlyn Briggs - krbriggs
 * CIS175 - Fall 2022
 * Nov 21, 2022
 */
package musiclibrary.beans;




import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Genre {
	@Id
	@GeneratedValue
	private int id;
	private String genreName;
	@OneToMany(targetEntity=Song.class)
	private List<Song> songs;
}
