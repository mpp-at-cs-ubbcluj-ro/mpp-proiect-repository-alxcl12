using Lab2C.Model;

namespace Lab2C.Repository
{
    /// <summary>
    /// Interface for a repository used to store bookings
    /// </summary>
    public interface IBookingRepo: IRepository<long, Booking>
    {
        Trip FindOneTrip(long tripId);

        Client FindOneClient(long clientId);
    }
}