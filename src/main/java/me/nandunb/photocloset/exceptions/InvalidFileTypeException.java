package me.nandunb.photocloset.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Invalid file type")
public class InvalidFileTypeException extends RuntimeException{

    public InvalidFileTypeException(String fileType){
        super(fileType + " is not a valid file type extension");
    }

}
