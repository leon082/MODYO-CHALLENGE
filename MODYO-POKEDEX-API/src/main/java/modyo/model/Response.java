package modyo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class Response {
    @Getter
    @Setter
    private Object data;
    @Getter
    @Setter
    private Integer statusCode;
    @Getter
    @Setter
    private String errorMessage;

}
