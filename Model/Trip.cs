using System;

namespace Lab2C.Model
{
    /// <summary>
    /// Class used to model a trip
    /// </summary>
    public class Trip: Entity<long>
    {
        private String Source { get; set; }
        private String Destination { get; set; }
        private DateTime DepartureTime { get; set; }
        private int FreeSeats { get; set; }

        public static int DefaultFreeSeats = 18;

        public Trip(string source, string destination, DateTime departureTime, int freeSeats)
        {
            Source = source;
            Destination = destination;
            DepartureTime = departureTime;
            FreeSeats = freeSeats;
        }
    }
}