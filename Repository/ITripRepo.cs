using Lab2C.Model;
using System.Collections.Generic;

namespace Lab2C.Repository
{
    /// <summary>
    /// Interface for a repository used to store trips
    /// </summary>
    public interface ITripRepo: IRepository<long, Trip>
    {
        IEnumerable<Booking> FindBookingsForTrip(long tripId);

        IEnumerable<Trip> FindTripsBySource(string source);
    }
}