/**
 * @author Kaitlyn Briggs - krbriggs
 * CIS175 - Fall 2022
 * Nov 30, 2022
 */
package musiclibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import musiclibrary.beans.Genre;

/**
 * @author Kaitlyn Briggs - krbriggs
 *
 */
public interface GenreRepository extends JpaRepository<Genre, Long>{

	List<Genre> findByGenreName(String genreName);

}
