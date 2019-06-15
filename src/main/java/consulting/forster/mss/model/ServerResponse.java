package consulting.forster.mss.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ServerResponse {

    private Object data;
    private String message;
    private HttpStatus statusCode;
}
