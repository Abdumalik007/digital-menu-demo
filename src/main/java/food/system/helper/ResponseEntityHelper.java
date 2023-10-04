package food.system.helper;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public class ResponseEntityHelper {
    public static ResponseEntity<?> NOT_FOUND(){
        return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).build();
    }

    public static ResponseEntity<?> INTERNAL_ERROR(){
        return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
    }

    public static ResponseEntity<?> OK_MESSAGE(){
        return ResponseEntity.ok("Ok");
    }

}
