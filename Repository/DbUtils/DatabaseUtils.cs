using System.Data;

namespace Lab2C.Repository.DbUtils
{
    public class DatabaseUtils
    {
        private static IDbConnection _instance = null;

        public static IDbConnection GetConnection()
        {
            if (_instance == null || _instance.State == System.Data.ConnectionState.Closed)
            {
                _instance = GetNewConnection();
                _instance.Open();
            }
            return _instance;
        }

        private static IDbConnection GetNewConnection()
        {
            return ConnectionFactory.GetInstance().CreateConnection();
        }
    }
}