/*
 *  @author albua
 *  created on 28/02/2021
 */

import java.io.Serializable;

/**
 * Interface used to implement classes that validate different entities
 * @param <T> type of entity
 */
public interface Validator<T> extends Serializable {
    void validate(T entity) throws ValidationException;
}
