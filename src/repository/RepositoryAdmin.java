package repository;/*
 *  @author albua
 *  created on 28/02/2021
 */

import model.Admin;
import model.validators.AdminValidator;

public class RepositoryAdmin implements AdminRepoInterface{
    AdminValidator Validator;

    @Override
    public Admin FindOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Admin> FindAll() {
        return null;
    }

    @Override
    public Admin Save(Admin entity) {
        return null;
    }

    @Override
    public Admin Delete(Long aLong) {
        return null;
    }

    @Override
    public Admin Update(Admin entity) {
        return null;
    }
}
