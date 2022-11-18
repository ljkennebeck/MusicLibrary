/**
 * @author Kaitlyn Briggs - krbriggs
 * CIS175 - Fall 2022
 * Nov 15, 2022
 */
package musicLibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import musicLibrary.beans.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long>{

}
