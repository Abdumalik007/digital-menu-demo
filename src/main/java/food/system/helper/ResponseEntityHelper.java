package food.system.helper;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public class ResponseEntityHelper {
    public static <T> ResponseEntity<T> NOT_FOUND(T data){
        return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(data);
    }

    public static ResponseEntity<String> BAD_REQUEST(String message){
        return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(message);
    }

    public static <T> ResponseEntity<T> INTERNAL_ERROR(T data){
        return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(data);
    }

    public static <T> ResponseEntity<T> OK_MESSAGE(T data){
        return ResponseEntity.ok(data);
    }

}
