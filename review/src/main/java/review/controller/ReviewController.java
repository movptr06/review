package review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import review.dto.ReviewDto;
import review.dto.ScoreDto;
import review.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

  @Autowired
  private ReviewService reviewService;

  @GetMapping
  @ResponseBody
  public ResponseEntity<ReviewDto.Response> readReviews(
      @RequestHeader(name = "X-User-Id", required = true) String reviewer) {

    ReviewDto.Response reviewResponseDto = reviewService.readReviewsByReviewer(reviewer);

    return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDto);
  }

  @GetMapping("/{reviewee}")
  @ResponseBody
  public ResponseEntity<ReviewDto.Response> readReviewsByReviewee(
      @PathVariable("reviewee") String reviewee) {

    ReviewDto.Response reviewResponseDto = reviewService.readReviewsByReviewee(reviewee);

    return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDto);
  }

  @PostMapping("/{reviewee}")
  @ResponseBody
  public ResponseEntity<ReviewDto.Response.Review> createReview(
      @PathVariable("reviewee") String reviewee,
      @Valid @RequestBody ReviewDto.Request reviewRequestDto,
      @RequestHeader(name = "X-User-Id", required = true) String reviewer) {

    ReviewDto.Command reviewCommandDto = new ReviewDto.Command(reviewee, reviewer,
        reviewRequestDto.score(), reviewRequestDto.comment());

    ReviewDto.Response.Review reviewDto = reviewService.createReview(reviewCommandDto);

    return ResponseEntity.status(HttpStatus.OK).body(reviewDto);
  }

  @DeleteMapping("/{reviewee}")
  @ResponseBody
  public ResponseEntity<Void> deleteReview(@PathVariable("reviewee") String reviewee,
      @RequestHeader(name = "X-User-Id", required = true) String reviewer) {

    reviewService.deleteReview(reviewee, reviewer);

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("/{reviewee}/score")
  @ResponseBody
  public ResponseEntity<ScoreDto.Response> readScore(@PathVariable("reviewee") String reviewee) {
    ScoreDto.Response scoreResponseDto = reviewService.readScore(reviewee);

    return ResponseEntity.status(HttpStatus.OK).body(scoreResponseDto);
  }

}
