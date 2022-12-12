package musiclibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import musiclibrary.beans.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
