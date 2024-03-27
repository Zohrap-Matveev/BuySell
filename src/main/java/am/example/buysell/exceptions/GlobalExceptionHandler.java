package am.example.buysell.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<HttpStatus> handleException(ProductNotFoundException e){
        ErrorResponse response = new ErrorResponse(
                "Product whit this id wasn't found",
                LocalDate.now()
        );
        return new ResponseEntity(response,HttpStatus.NOT_FOUND);
    }
}
