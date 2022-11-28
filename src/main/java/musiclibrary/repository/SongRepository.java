package musiclibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import musiclibrary.beans.Genre;
import musiclibrary.beans.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
	
	//these functions are auto created by the repo based on the given name

	List<Song> findByTitle(String title);
	
	List<Song> findByArtist(String artist);
	
	List<Song> findByGenre(String genre);
	
	List<Song> findByTitleAndArtist(String title, String artist);
	
	List<Song> findByTitleAndGenre(String title, String genre);
	
	List<Song> findByGenreAndArtist(String genre, String artist);
	
	List<Song> findByTitleAndArtistAndGenre(String title, String artist, String genre);
			
}
