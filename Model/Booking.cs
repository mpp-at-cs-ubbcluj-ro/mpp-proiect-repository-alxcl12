namespace Lab2C.Model
{
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