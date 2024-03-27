package am.example.buysell.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ErrorResponse{

    private String message;
    private LocalDate timestamp;

    public ErrorResponse(String message, LocalDate timestamp){
        this.message = message;
        this.timestamp = timestamp;
    }
}
