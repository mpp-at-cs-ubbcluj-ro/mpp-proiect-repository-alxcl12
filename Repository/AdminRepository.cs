using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using Lab2C.Model;
using Lab2C.Model.Validators;
using Lab2C.Repository.DbUtils;
using log4net;

namespace Lab2C.Repository
{
    public class AdminRepository: IAdminRepo
    {
        private readonly AdminValidator _validator;
        
        private static readonly ILog Logger = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        
        public AdminRepository(AdminValidator validator)
        {
            Logger.Info("Initializing AdminRepository");

            _validator = validator;
        }
        
        public Admin FindOne(long id)
        {
            Logger.InfoFormat("Entering FindOne with value {0}", id);

            var con = DbUtils.DatabaseUtils.GetConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Admins where id=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        var username = dataR.GetString(1);
                        var passHash = dataR.GetString(2);

                        var admin = new Admin(username, passHash);
                        admin.Id = id;
                        
                        Logger.InfoFormat("Exiting FindOne with value {0}", admin);
                        return admin;
                    }
                }
            }
            Logger.InfoFormat("Exiting FindOne with value {0}", null);
            
            return null;
        }

        public IEnumerable<Admin> FindAll()
        {
            Logger.Info("Entering FindAll");

            var con = DbUtils.DatabaseUtils.GetConnection();

            IList<Admin> admins = new List<Admin>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Admins";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        var id = dataR.GetInt64(0);
                        var username = dataR.GetString(1);
                        var passHash = dataR.GetString(2);

                        var admin = new Admin(username, passHash);
                        admin.Id = id;
                        
                        admins.Add(admin);
                    }
                }
            }
            Logger.Info("Exiting FindAll");
            
            return admins;
        }

        public void Save(Admin entity)
        {
            Logger.InfoFormat("Entering Save with value {0}", entity);
            
            var con = DatabaseUtils.GetConnection();

            try
            {
                _validator.Validate(entity);
            }
            catch (ValidationException e)
            {
                Logger.ErrorFormat("Invalid admin {0}", entity);
            }
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Admins(username, passwordHash)  values (@user, @pass)";

                var paramUser = comm.CreateParameter();
                paramUser.ParameterName = "@user";
                paramUser.Value = entity.Username;
                comm.Parameters.Add(paramUser);

                var paramPass = comm.CreateParameter();
                paramPass.ParameterName = "@pass";
                paramPass.Value = entity.PasswordHash;
                comm.Parameters.Add(paramPass);
                
                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    Logger.ErrorFormat("Admin {0} not saved", entity);
                    throw new Exception("No admin added !");
                }
            }
            Logger.Info("Exiting Save");
        }

        public void Delete(long id)
        {
            Logger.InfoFormat("Entering Delete with id of {0}", id);
            
            var con = DatabaseUtils.GetConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Admins where id=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                
                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0)
                {
                    Logger.ErrorFormat("Admin with id {0} not deleted", id);
                    throw new Exception("No admin deleted!");
                }
            }
            
            Logger.Info("Exiting Delete");
        }

        public void Update(Admin entity)
        {
            throw new System.NotImplementedException();
        }
    }
}