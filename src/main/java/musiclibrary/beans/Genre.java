/**
 * @author Kaitlyn Briggs - krbriggs
 * CIS175 - Fall 2022
 * Nov 21, 2022
 */
package musiclibrary.beans;




import java.util.List;

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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name="genre")
public class Genre {
	@Id
	@GeneratedValue
	private long id;
	private String genreName;
	@Autowired
	@OneToMany(targetEntity=Song.class, mappedBy = "genre")
	private List<Song> songs;
}
