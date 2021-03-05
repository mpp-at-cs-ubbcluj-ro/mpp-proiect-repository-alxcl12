package model.validators;/*
 *  @author albua
 *  created on 28/02/2021
 */

import model.validators.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
