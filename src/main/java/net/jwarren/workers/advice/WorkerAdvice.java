package net.jwarren.workers.advice;

import net.jwarren.workers.misc.WorkerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WorkerAdvice {

    @ResponseBody
    @ExceptionHandler(WorkerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String workerNotFoundHandler(WorkerNotFoundException exception) {
        return exception.getMessage();
    }
}
