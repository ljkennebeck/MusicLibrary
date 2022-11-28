/**
 * @author Kaitlyn Briggs - krbriggs
 * CIS175 - Fall 2022
 * Nov 28, 2022
 */
package musiclibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import musiclibrary.beans.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
	
	List<Playlist> findByPlaylistName(String playlistName);

}
