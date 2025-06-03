package review.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import review.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findAllByReviewee(String reviewee);

  List<Review> findAllByReviewer(String reviewer);

  @Query("""
      SELECT review FROM Review review WHERE reviewee = :reviewee
      AND id >= :cursor ORDER BY id ASC LIMIT :limit """)
  List<Review> findPageByReviewee(@Param("reviewee") String reviewee, @Param("cursor") Long cursor,
      @Param("limit") Long limit);

  @Query("""
      SELECT review FROM Review review WHERE reviewer = :reviewer
      AND id >= :cursor ORDER BY id ASC LIMIT :limit """)
  List<Review> findPageByReviewer(@Param("reviewer") String reviewer, @Param("cursor") Long cursor,
      @Param("limit") Long limit);

  void deleteByRevieweeAndReviewer(String reviewee, String reviewer);

  Long countByReviewee(String reviewee);

  @Query("SELECT avg(review.score) FROM Review review WHERE reviewee = :reviewee")
  Float averageByReviewee(@Param("reviewee") String reviewee);

}
