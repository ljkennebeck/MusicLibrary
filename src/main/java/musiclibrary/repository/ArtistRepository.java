/**
 * @author Kaitlyn Briggs - krbriggs
 * CIS175 - Fall 2022
 * Nov 15, 2022
 */
package musiclibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import musiclibrary.beans.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long>{

}
