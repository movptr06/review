package review.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import review.domain.Review;
import review.dto.ReviewDto;
import review.dto.ScoreDto;
import review.repository.ReviewRepository;

@Service
@Transactional
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	public ReviewDto.Response readReviewsByReviewer(String reviewer) {
		List<Review> reviews = reviewRepository.findAllByReviewer(reviewer);

		return new ReviewDto.Response(reviews.stream().map(review -> new ReviewDto.Response.Review(
				review.getId(),
				review.getReviewee(),
				review.getReviewer(),
				review.getScore(),
				review.getComment()
		)).collect(Collectors.toList()));
	}

	public ReviewDto.Response readReviewsByReviewee(String reviewee) {
		List<Review> reviews = reviewRepository.findAllByReviewee(reviewee);

		return new ReviewDto.Response(reviews.stream().map(review -> new ReviewDto.Response.Review(
				review.getId(),
				review.getReviewee(),
				review.getReviewer(),
				review.getScore(),
				review.getComment()
		)).collect(Collectors.toList()));
	}

	public ReviewDto.Response.Review createReview(ReviewDto.Command reviewCommandDto) {
		Review review = new Review(
				reviewCommandDto.reviewee(),
				reviewCommandDto.reviewer(),
				reviewCommandDto.score(),
				reviewCommandDto.comment()
		);

		Review savedReview = reviewRepository.save(review);

		return new ReviewDto.Response.Review(
				savedReview.getId(),
				savedReview.getReviewee(),
				savedReview.getReviewer(),
				savedReview.getScore(),
				savedReview.getComment()
		);
	}

	public void deleteReview(String reviewee, String reviewer) {
		reviewRepository.deleteByRevieweeAndReviewer(reviewee, reviewer);
	}

	public ScoreDto.Response readScore(String reviewee) {
		Float score = reviewRepository.selectAverageByReviewee(reviewee);

		return new ScoreDto.Response(score);
	}

}
