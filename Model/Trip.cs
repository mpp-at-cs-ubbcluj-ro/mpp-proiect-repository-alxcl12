using System;

namespace Lab2C.Model
{
    /// <summary>
    /// Class used to model a trip
    /// </summary>
    public class Trip: Entity<long>
    {
        public String Source { get; set; }
        public String Destination { get; set; }
        public DateTime DepartureTime { get; set; }
        public int FreeSeats { get; set; }

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