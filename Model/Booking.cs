using System;

namespace Lab2C.Model
{
    /// <summary>
    /// Class used to model a booking for one client and one trip
    /// </summary>
    public class Booking: Entity<long>
    {
        public Trip Trip { get; set; }
        public int NrSeats { get; set; }

        public String ClientFirstName { get; set; }
        
        public String ClientLastName { get; set; }

        public Booking(Trip tripId, int nrSeats, String clientFirstName, String clientLastName)
        {
            Trip = tripId;
            NrSeats = nrSeats;
            ClientFirstName = clientFirstName;
            ClientLastName = clientLastName;
        }
    }
}