package review.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import review.ApplicationProperties;
import review.domain.Review;
import review.dto.ReviewDto;
import review.dto.ScoreDto;
import review.exception.ScoreNotAllowedException;
import review.repository.ReviewRepository;

@Service
@Transactional
public class ReviewService {

  @Autowired
  private ApplicationProperties applicationProperties;

  @Autowired
  private ReviewRepository reviewRepository;

  public ReviewDto.Response readReviewsByReviewer(String reviewer, Long cursor, Long limit) {
    List<Review> reviews = reviewRepository.findPageByReviewer(reviewer, cursor, limit);

    return new ReviewDto.Response(reviews.stream()
        .map(review -> new ReviewDto.Response.Review(review.getId(), review.getReviewee(),
            review.getReviewer(), review.getScore(), review.getComment()))
        .collect(Collectors.toList()));
  }

  public ReviewDto.Response readReviewsByReviewee(String reviewee, Long cursor, Long limit) {
    List<Review> reviews = reviewRepository.findPageByReviewee(reviewee, cursor, limit);

    return new ReviewDto.Response(reviews.stream()
        .map(review -> new ReviewDto.Response.Review(review.getId(), review.getReviewee(),
            review.getReviewer(), review.getScore(), review.getComment()))
        .collect(Collectors.toList()));
  }

  public ReviewDto.Response.Review createReview(ReviewDto.Command reviewCommandDto) {
    if (!applicationProperties.isAllowedScore(reviewCommandDto.score())) {
      throw new ScoreNotAllowedException();
    }

    Review review = new Review(reviewCommandDto.reviewee(), reviewCommandDto.reviewer(),
        reviewCommandDto.score(), reviewCommandDto.comment());

    Review savedReview = reviewRepository.save(review);

    return new ReviewDto.Response.Review(savedReview.getId(), savedReview.getReviewee(),
        savedReview.getReviewer(), savedReview.getScore(), savedReview.getComment());
  }

  public void deleteReview(String reviewee, String reviewer) {
    reviewRepository.deleteByRevieweeAndReviewer(reviewee, reviewer);
  }

  public ScoreDto.Response readScore(String reviewee) {
    Long count = reviewRepository.countByReviewee(reviewee);
    Float score = reviewRepository.averageByReviewee(reviewee);

    return new ScoreDto.Response(count, score);
  }

}
