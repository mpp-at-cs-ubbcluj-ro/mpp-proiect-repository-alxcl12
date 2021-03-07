namespace Lab2C.Model
{
    /// <summary>
    /// Class used to model a booking for one client and one trip
    /// </summary>
    public class Booking: Entity<long>
    {
        private long ClientId { get; set; }
        private long TripId { get; set; }
        private int NrSeats { get; set; }

        public Booking(long clientId, long tripId, int nrSeats)
        {
            ClientId = clientId;
            TripId = tripId;
            NrSeats = nrSeats;
        }
    }
}