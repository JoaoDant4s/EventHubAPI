package imd.eventhub.restAPI.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class RestSuccessMessage {
    private HttpStatus status;
    private String message;
}
