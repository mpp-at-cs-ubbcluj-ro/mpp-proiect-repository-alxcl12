using Lab2C.Model;

namespace Lab2C.Repository
{
    /// <summary>
    /// Interface for a repository used to store trips
    /// </summary>
    public interface ITripRepo: IRepository<long, Trip>
    {
        
    }
}