package co.jlv.livrokaz.domain.exception.wrapper;

import co.jlv.livrokaz.domain.exception.UploadFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApplicationExceptionsWrapper {

  @ExceptionHandler(UploadFileException.class)
  public ResponseEntity<String> processUploadFileException(
      HttpServletRequest httpServletRequest,
      UploadFileException e
  ) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }
}