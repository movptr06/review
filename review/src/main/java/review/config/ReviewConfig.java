package review.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "review")
public class ReviewConfig {

	private List<Float> scores;

	void setScores(List<Float> scores) {
		this.scores = scores;
	}

	public boolean isAllowedScore(Float score) {
		return this.scores.contains(score);
	}

}
