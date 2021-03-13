namespace Lab2C.Model
{
    /// <summary>
    /// Class used to model a booking for one client and one trip
    /// </summary>
    public class Booking: Entity<long>
    {
        public Client Client { get; set; }
        public Trip Trip { get; set; }
        public int NrSeats { get; set; }

        public Booking(Client clientId, Trip tripId, int nrSeats)
        {
            Client = clientId;
            Trip = tripId;
            NrSeats = nrSeats;
        }
    }
}