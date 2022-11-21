package musiclibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import musiclibrary.beans.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

	List<Song> findByTitle(String title);
	
	List<Song> findByArtist(String artist);
	
	List<Song> findByTitleAndArtist(String title, String artist);
			
}
