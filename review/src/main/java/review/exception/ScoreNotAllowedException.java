package review.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ScoreNotAllowedException extends RuntimeException {

  private static final long serialVersionUID = 1L;

}
