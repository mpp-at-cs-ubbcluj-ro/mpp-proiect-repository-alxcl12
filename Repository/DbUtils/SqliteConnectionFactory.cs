using System.Data;
using System.Data.SQLite;
using Mono.Data.Sqlite;

namespace Lab2C.Repository.DbUtils
{
    public class SqliteConnectionFactory: ConnectionFactory
    {
        public override IDbConnection CreateConnection()
        {
            const string connectionString = "URI=file:C:/Facultate/New/AnulII/SemestrulIV/MPP/Laboratoare/Database/problem4db";
            //return new SqliteConnection(connectionString);
            return new SQLiteConnection(connectionString);
        }
    }
}