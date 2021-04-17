/**
 * Default interface used to store an entity with CRUD operations
 * @param <ID> id of entity to be stored
 * @param <E> stored entity
 */
public interface Repository<ID, E extends Entity<ID>> {
    E findOne(ID id);

    Iterable<E> findAll();

    void save(E entity);

    void delete(ID id);

    void update(E entity);

}