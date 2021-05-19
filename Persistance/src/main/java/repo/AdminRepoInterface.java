package repo;/*
 *  @author albua
 *  created on 04/03/2021
 */


import models.Admin;

import java.security.NoSuchAlgorithmException;

/**
 * Interface for a repository used to store admins
 */
public interface AdminRepoInterface extends Repository<Long, Admin> {

    Admin authenticateAdmin(String username, String password) throws NoSuchAlgorithmException;
}
