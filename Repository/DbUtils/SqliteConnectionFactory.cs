using System.Configuration;
using System.Data;
using System.Data.SQLite;
using Mono.Data.Sqlite;

namespace Lab2C.Repository.DbUtils
{
    public class SqliteConnectionFactory: ConnectionFactory
    {
        public override IDbConnection CreateConnection()
        {
            //const string connectionString = "URI=file:C:/Facultate/New/AnulII/SemestrulIV/MPP/Laboratoare/Database/problem4db";
            var connectionString = ConfigurationManager.ConnectionStrings["problem4db"].ConnectionString;
            //return new SqliteConnection(connectionString);
            return new SQLiteConnection(connectionString);
        }
    }
}