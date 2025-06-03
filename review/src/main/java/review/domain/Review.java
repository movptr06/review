package review.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public final class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String reviewee;
  @NotNull
  private String reviewer;

  @NotNull
  private Float score;
  private String comment;

  public Long getId() {
    return this.id;
  }

  public String getReviewee() {
    return this.reviewee;
  }

  public String getReviewer() {
    return this.reviewer;
  }

  public Float getScore() {
    return this.score;
  }

  public String getComment() {
    return this.comment;
  }

  public Review() {}

  public Review(String reviewee, String reviewer, Float score, String comment) {
    this.reviewee = reviewee;
    this.reviewer = reviewer;
    this.score = score;
    this.comment = comment;
  }

}
