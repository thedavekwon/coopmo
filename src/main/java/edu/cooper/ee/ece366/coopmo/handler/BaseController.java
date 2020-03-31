package edu.cooper.ee.ece366.coopmo.handler;

// TODO (migrate to BaseExceptionHandler)
public class BaseController {
    protected void checkEmpty(String input, String field) throws BaseExceptionHandler.EmptyFieldException {
        if (input.equals("")) {
            throw new BaseExceptionHandler.EmptyFieldException("Empty " + field);
        }
    }

    protected void checkPositive(Long input, String field) throws BaseExceptionHandler.InValidFieldValueException {
        if (input <= 0) {
            throw new BaseExceptionHandler.InValidFieldValueException("Non-positive " + field + " entered");
        }
    }
}
