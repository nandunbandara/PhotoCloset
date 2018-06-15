package me.nandunb.photocloset.preprocessors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;


public class ResponsePreprocessor {

    private static final String SUCCESS_MESSAGE = "Photo uploading successful!";
    private static final String FAILURE_MESSAGE = "Photo uploading unsuccessful!";

    public static ResponseEntity<Object> sendSuccessResponse(String fileUrl){
        HashMap<String, Object> responseObject = new HashMap<>();

        responseObject.put("success", true);
        responseObject.put("fileUrl", fileUrl);
        responseObject.put("message", SUCCESS_MESSAGE);

        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
    }

    public static ResponseEntity<Object> sendFailureResponse(Exception e){
        HashMap<String, Object> responseObject = new HashMap<>();

        responseObject.put("success", false);
        responseObject.put("message", FAILURE_MESSAGE);
        responseObject.put("error", e.getMessage());

        return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
