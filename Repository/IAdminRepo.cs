using Lab2C.Model;

namespace Lab2C.Repository
{
    /// <summary>
    /// Interface for a repository used to store admins
    /// </summary>
    public interface IAdminRepo: IRepository<long, Admin>
    {
        
    }
}