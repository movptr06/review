package review.dto;

import java.util.List;
import jakarta.validation.constraints.NotNull;

public class ReviewDto {

  public record Request(@NotNull Float score, String comment) {
  }

  public record Command(String reviewee, String reviewer, Float score, String comment) {
  }

  public record Response(List<Review> Reviews) {

    public record Review(Long id, String reviewee, String reviewer, Float score, String comment) {
    }

  }

}
