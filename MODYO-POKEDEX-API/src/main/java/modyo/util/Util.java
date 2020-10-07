package modyo.util;

import modyo.model.Response;

public class Util {
    public static Response buildErrorResponse(Integer statusCode, String errorMessage){
        return Response.builder().statusCode(statusCode).errorMessage(errorMessage).build();
    }


}
