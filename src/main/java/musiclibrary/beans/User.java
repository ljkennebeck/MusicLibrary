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
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue
	private int id;
	private String username;
	private String password;
	private boolean isDev;
	@Autowired
	@OneToMany(targetEntity=Playlist.class)
	private List<Playlist> playlists;
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	
	
	
	
}