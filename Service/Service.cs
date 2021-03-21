using Lab2C.Repository;
using Lab2C.Model;
using System.Collections.Generic;
using System;

namespace Lab2C.Service
{
    public class Service
    {
        private AdminRepository adminRepo;
        private BookingRepository bookingRepo;
        private TripRepository tripRepo;


        public Service(AdminRepository admin, BookingRepository booking, TripRepository trip)
        {
            this.adminRepo = admin;
            this.bookingRepo = booking;
            this.tripRepo = trip;
        }

        public bool Login(string username, string password)
        {
            Admin admin = adminRepo.AuthenticateAdmin(username, password);

            if (admin != null)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public IEnumerable<Trip> GetAllTrips()
        {
            return tripRepo.FindAll();
        }

        public IEnumerable<Booking> GetBookingsForTrip(long tripId)
        {
            return tripRepo.FindBookingsForTrip(tripId);
        }

        public void AddBooking(long tripId, string firstName, string lastName, int seats)
        {
            Trip trip = tripRepo.FindOne(tripId);

            Booking booking = new Booking(trip, seats, firstName, lastName);

            bookingRepo.Save(booking);
            trip.FreeSeats -= seats;

            tripRepo.Update(trip);
        }

        public IEnumerable<Trip> GetTripsBySourceAndDate(string source, DateTime date)
        {
            var res = tripRepo.FindTripsBySource(source);
            IList<Trip> result = new List<Trip>();

            //filer by data when data is fixed
            foreach(Trip tr in res)
            {
                if(tr.DepartureTime.Day == date.Day && tr.DepartureTime.Year == date.Year && tr.DepartureTime.Month == date.Month)
                {
                    result.Add(tr);
                }
            }


            return result;
        }
    }
}