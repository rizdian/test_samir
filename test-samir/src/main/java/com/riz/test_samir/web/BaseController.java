package com.riz.test_samir.web;

import com.riz.test_samir.web.exception.BadRequestException;
import com.riz.test_samir.web.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Data
@ControllerAdvice
public class BaseController {

    @Setter(value = AccessLevel.PRIVATE)
    @Getter(value = AccessLevel.PRIVATE)
    private ResponseEntity<Object> response = null;

    public ResponseEntity<Object> buildResponseGeneralSuccess() {
        return buildResponseGeneralSuccess(null);
    }
    public ResponseEntity<Object> buildResponseDataCreated() {
        return buildResponseDataCreated(null);
    }


    public ResponseEntity<Object> buildResponseGeneralSuccess(Object data) {
        setResponse(new ResponseEntity<>(ResponseWrapper.build(data, ResponseStatus.GENERAL_SUCCESS), HttpStatus.OK));
        return this.response;
    }
    public ResponseEntity<Object> buildResponseDataCreated(Object data) {
        setResponse(new ResponseEntity<>(ResponseWrapper.build(data, ResponseStatus.DATA_CREATED), HttpStatus.CREATED));
        return this.response;
    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception) {
        return returnErrorFormatted(exception, ResponseStatus.GENERAL_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class})
    public final ResponseEntity<Object> handleBadRequestException(BadRequestException exception) {
        return returnErrorFormatted(exception, ResponseStatus.GENERAL_WARN, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> returnErrorFormatted(Exception exception, ResponseStatus responseStatus, HttpStatus httpStatus){
        return new ResponseEntity<>(ResponseWrapper.build(exception.getMessage(), responseStatus), httpStatus);
    }

}
