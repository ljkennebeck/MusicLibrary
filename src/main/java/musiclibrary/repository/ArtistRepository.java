/**
 * @author Kaitlyn Briggs - krbriggs
 * CIS175 - Fall 2022
 * Nov 15, 2022
 */
package musiclibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import musiclibrary.beans.Artist;
import musiclibrary.beans.Playlist;



public interface ArtistRepository extends JpaRepository<Artist, Long>{
	List<Artist> findByArtistName(String artistName);
}
