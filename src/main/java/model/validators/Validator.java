/*
 *  @author albua
 *  created on 28/02/2021
 */
package model.validators;
import model.validators.ValidationException;

/**
 * Interface used to implement classes that validate different entities
 * @param <T> type of entity
 */
public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
