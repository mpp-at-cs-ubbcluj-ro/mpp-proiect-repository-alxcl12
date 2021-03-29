/*
 *  @author albua
 *  created on 28/02/2021
 */

/**
 * Interface used to implement classes that validate different entities
 * @param <T> type of entity
 */
public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
