/*
 *  @author albua
 *  created on 28/02/2021
 */
package repository;
import model.Entity;

public interface Repository<ID, E extends Entity<ID>> {
    E FindOne(ID id);

    Iterable<E> FindAll();

    E Save(E entity);

    E Delete(ID id);


    E Update(E entity);

}