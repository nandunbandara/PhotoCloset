package me.nandunb.photocloset.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Missing file")
public class MissingFileException extends RuntimeException {

    public MissingFileException(){
        super("File not selected");
    }

}
