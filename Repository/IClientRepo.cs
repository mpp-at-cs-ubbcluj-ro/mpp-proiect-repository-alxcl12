using Lab2C.Model;

namespace Lab2C.Repository
{
    /// <summary>
    /// Interface for a repository used to store clients
    /// </summary>
    public interface IClientRepo: IRepository<long, Client>
    {
        
    }
}