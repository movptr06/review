package review;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "review")
public class ApplicationProperties {

  private List<Float> scores;

  void setScores(List<Float> scores) {
    this.scores = scores;
  }

  public boolean isAllowedScore(Float score) {
    return this.scores.contains(score);
  }

}
