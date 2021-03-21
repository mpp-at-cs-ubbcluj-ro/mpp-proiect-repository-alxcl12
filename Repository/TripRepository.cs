using System;
using System.Collections.Generic;
using Lab2C.Model;
using Lab2C.Model.Validators;
using Lab2C.Repository.DbUtils;
using log4net;
using System.Globalization;

namespace Lab2C.Repository
{
    public class TripRepository: ITripRepo
    {
        private readonly TripValidator _validator;
        
        private static readonly ILog Logger = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        public TripRepository(TripValidator validator)
        {
            Logger.Info("Initializing TripRepository");

            _validator = validator;
        }
        
        public Trip FindOne(long id)
        {
            Logger.InfoFormat("Entering FindOne with value {0}", id);

            var con = DbUtils.DatabaseUtils.GetConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Trips where id=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        var source = dataR.GetString(1);
                        var destination = dataR.GetString(2);
                        var depTime = dataR.GetDateTime(3);
                        var freeSeats = dataR.GetInt32(4);

                        var trip = new Trip(source, destination, depTime, freeSeats);
                        trip.Id = id;
                        
                        Logger.InfoFormat("Exiting FindOne with value {0}", trip);
                        return trip;
                    }
                }
            }
            Logger.InfoFormat("Exiting FindOne with value {0}", null);
            
            return null;
        }

        public IEnumerable<Trip> FindAll()
        {
            Logger.Info("Entering FindAll");

            var con = DatabaseUtils.GetConnection();

            IList<Trip> trips = new List<Trip>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Trips";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        var id = dataR.GetInt64(0);
                        var source = dataR.GetString(1);
                        var destination = dataR.GetString(2);

                        var time = dataR.GetDateTime(3);

                        var freeSeats = dataR.GetInt32(4);

                        var trip = new Trip(source, destination, time, freeSeats);
                        trip.Id = id;
                        
                        trips.Add(trip);
                    }
                }
            }
            Logger.Info("Exiting FindAll");
            
            return trips;
        }

        public void Save(Trip entity)
        {
            Logger.InfoFormat("Entering Save with value {0}", entity);
            
            var con = DatabaseUtils.GetConnection();

            try
            {
                _validator.Validate(entity);
            }
            catch (ValidationException)
            {
                Logger.ErrorFormat("Invalid trip {0}", entity);
            }
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Trips(sourceCity, destinationCity, departureTime, freeSeats)  values (@source, @dest, @time, @seats)";

                var paramSource = comm.CreateParameter();
                paramSource.ParameterName = "@source";
                paramSource.Value = entity.Source;
                comm.Parameters.Add(paramSource);

                var paramDest = comm.CreateParameter();
                paramDest.ParameterName = "@dest";
                paramDest.Value = entity.Destination;
                comm.Parameters.Add(paramDest);
                
                var paramTime = comm.CreateParameter();
                paramTime.ParameterName = "@time";
                paramTime.Value = entity.DepartureTime;
                comm.Parameters.Add(paramTime);
                
                var paramSeats = comm.CreateParameter();
                paramSeats.ParameterName = "@seats";
                paramSeats.Value = entity.FreeSeats;
                comm.Parameters.Add(paramSeats);
                
                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    Logger.ErrorFormat("Trip {0} not saved", entity);
                    throw new Exception("No trip added !");
                }
            }
            Logger.Info("Exiting Save");
        }

        public void Delete(long id)
        {
            Logger.InfoFormat("Entering Delete with id of {0}", id);
            
            var con = DatabaseUtils.GetConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Trips where id=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                
                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0)
                {
                    Logger.ErrorFormat("Trip with id {0} not deleted", id);
                    throw new Exception("No trip deleted!");
                }
            }
            
            Logger.Info("Exiting Delete");
        }

        public void Update(Trip entity)
        {
            Logger.InfoFormat("Entering Update with id of {0}", entity.Id);

            var con = DatabaseUtils.GetConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Trips set freeSeats=@seats where ID=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = entity.Id;
                comm.Parameters.Add(paramId);

                var paramS = comm.CreateParameter();
                paramS.ParameterName = "@seats";
                paramS.Value = entity.FreeSeats;
                comm.Parameters.Add(paramS);

                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0)
                {
                    Logger.ErrorFormat("Trip with id {0} not deleted", entity.Id);
                    throw new Exception("No trip deleted!");
                }
            }

            Logger.Info("Exiting Delete");
        }

        public IEnumerable<Booking> FindBookingsForTrip(long tripId)
        { 
            var con = DbUtils.DatabaseUtils.GetConnection();

            IList<Booking> bookings = new List<Booking>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Bookings where tripId=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = tripId;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        var id = dataR.GetInt64(0);
                        var nrSeats = dataR.GetInt32(2);
                        var clientFirstName = dataR.GetString(3);
                        var clientLastName = dataR.GetString(4);

                        var trip = FindOne(tripId);

                        if (trip != null)
                        {
                            var booking = new Booking(trip, nrSeats, clientFirstName, clientLastName);
                            booking.Id = id;
                            bookings.Add(booking);
                        }
                    }
                }
            }
            Logger.Info("Exiting FindAll");

            return bookings;
        }


        public IEnumerable<Trip> FindTripsBySource(string source)
        {
            var con = DatabaseUtils.GetConnection();

            IList<Trip> trips = new List<Trip>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Trips where sourceCity=@city";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@city";
                paramId.Value = source;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        var id = dataR.GetInt64(0);
                        var destination = dataR.GetString(2);

                        var time = dataR.GetDateTime(3);

                        var freeSeats = dataR.GetInt32(4);

                        var trip = new Trip(source, destination, time, freeSeats);
                        trip.Id = id;

                        trips.Add(trip);
                    }
                }
            }
            Logger.Info("Exiting FindAll");

            return trips;
        }
    }
}