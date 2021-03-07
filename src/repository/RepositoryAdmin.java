/*
 *  @author albua
 *  created on 28/02/2021
 */
package repository;
import model.Admin;
import model.validators.AdminValidator;

/**
 * Repository used to store admins
 */
public class RepositoryAdmin implements AdminRepoInterface{
    AdminValidator Validator;

    @Override
    public Admin findOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Admin> findAll() {
        return null;
    }

    @Override
    public Admin save(Admin entity) {
        return null;
    }

    @Override
    public Admin delete(Long aLong) {
        return null;
    }

    @Override
    public Admin update(Admin entity) {
        return null;
    }
}
