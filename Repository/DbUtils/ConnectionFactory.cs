using System;
using System.Data;
using System.Reflection;

namespace Lab2C.Repository.DbUtils
{
    public abstract class ConnectionFactory
    {
        protected ConnectionFactory()
        {
        }

        private static ConnectionFactory _instance;

        public static ConnectionFactory GetInstance()
        {
            if (_instance == null)
            {

                var assembler = Assembly.GetExecutingAssembly();
                var types = assembler.GetTypes();
                foreach (var type in types)
                {
                    if (type.IsSubclassOf(typeof(ConnectionFactory)))
                        _instance = (ConnectionFactory)Activator.CreateInstance(type);
                }
            }
            return _instance;
        }

        public abstract  IDbConnection CreateConnection();
    }
}
