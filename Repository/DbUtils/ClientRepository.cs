using System;
using System.Collections.Generic;
using Lab2C.Model;
using Lab2C.Model.Validators;
using log4net;

namespace Lab2C.Repository.DbUtils
{
    public class ClientRepository: IClientRepo
    {
        private readonly ClientValidator _validator;
        
        private static readonly ILog Logger = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        public ClientRepository(ClientValidator validator)
        {
            Logger.Info("Initializing ClientRepository");

            _validator = validator;
        }
        
        public Client FindOne(long id)
        {
            Logger.InfoFormat("Entering FindOne with value {0}", id);

            var con = DbUtils.DatabaseUtils.GetConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Clients where id=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        var firstName = dataR.GetString(1);
                        var lastName = dataR.GetString(2);

                        var client = new Client(firstName, lastName);
                        client.Id = id;
                        
                        Logger.InfoFormat("Exiting FindOne with value {0}", client);
                        return client;
                    }
                }
            }
            Logger.InfoFormat("Exiting FindOne with value {0}", null);
            
            return null;
        }

        public IEnumerable<Client> FindAll()
        {
            Logger.Info("Entering FindAll");

            var con = DbUtils.DatabaseUtils.GetConnection();

            IList<Client> clients = new List<Client>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Clients";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        var id = dataR.GetInt64(0);
                        var firstName = dataR.GetString(1);
                        var lastName = dataR.GetString(2);

                        var client = new Client(firstName, lastName);
                        client.Id = id;
                        
                        clients.Add(client);
                    }
                }
            }
            Logger.Info("Exiting FindAll");
            
            return clients;
        }

        public void Save(Client entity)
        {
            Logger.InfoFormat("Entering Save with value {0}", entity);
            
            var con = DatabaseUtils.GetConnection();

            try
            {
                _validator.Validate(entity);
            }
            catch (ValidationException)
            {
                Logger.ErrorFormat("Invalid client {0}", entity);
            }
            
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Clients(firstName, lastName)  values (@first, @last)";

                var paramFirst = comm.CreateParameter();
                paramFirst.ParameterName = "@first";
                paramFirst.Value = entity.FirstName;
                comm.Parameters.Add(paramFirst);

                var paramLast = comm.CreateParameter();
                paramLast.ParameterName = "@last";
                paramLast.Value = entity.LastName;
                comm.Parameters.Add(paramLast);
                
                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    Logger.ErrorFormat("Client {0} not saved", entity);
                    throw new Exception("No Client added !");
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
                comm.CommandText = "delete from Clients where id=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                
                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0)
                {
                    Logger.ErrorFormat("Client with id {0} not deleted", id);
                    throw new Exception("No client deleted!");
                }
            }
            
            Logger.Info("Exiting Delete");
        }

        public void Update(Client entity)
        {
            throw new System.NotImplementedException();
        }
    }
}