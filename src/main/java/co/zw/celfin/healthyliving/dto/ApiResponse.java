package co.zw.celfin.healthyliving.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private int code;
    private T data;
    private String message;

    public static <T> ApiResponse<T> of(HttpStatus status, T data, String message) {
        return new ApiResponse<>(status.value(), data, message);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return of(HttpStatus.OK, data, "successful");
    }

    public static <T> ApiResponse<T> created(T data) {
        return of(HttpStatus.CREATED, data, "successful");
    }
}
