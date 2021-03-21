using System;
using System.Collections.Generic;
using Lab2C.Model;
using Lab2C.Model.Validators;
using Lab2C.Repository.DbUtils;
using log4net;

namespace Lab2C.Repository
{
    public class BookingRepository: IBookingRepo
    {
        private readonly BookingValidator _validator;
        
        private static readonly ILog Logger = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        
        public BookingRepository(BookingValidator validator)
        {
            Logger.Info("Initializing BookingRepository");

            _validator = validator;
        }

        public Booking FindOne(long id)
        {
            Logger.InfoFormat("Entering FindOne with value {0}", id);

            var con = DbUtils.DatabaseUtils.GetConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Bookings where id=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        var tripId = dataR.GetInt64(1);
                        var nrSeats = dataR.GetInt32(2);
                        var clientFirstName = dataR.GetString(3);
                        var clientLastName = dataR.GetString(4);

                        var trip = FindOneTrip(tripId);

                        if (trip != null)
                        {
                            var booking = new Booking(trip, nrSeats, clientFirstName, clientLastName);
                            booking.Id = id;
                            Logger.InfoFormat("Exiting FindOne with value {0}", booking);
                            return booking;
                        }
                    }
                }
            }
            Logger.InfoFormat("Exiting FindOne with value {0}", null);
            
            return null;
        }

        public IEnumerable<Booking> FindAll()
        {
            Logger.Info("Entering FindAll");

            var con = DbUtils.DatabaseUtils.GetConnection();

            IList<Booking> bookings = new List<Booking>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Bookings";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        var id = dataR.GetInt64(0);
                        var tripId = dataR.GetInt64(1);
                        var nrSeats = dataR.GetInt32(2);
                        var clientFirstName = dataR.GetString(3);
                        var clientLastName = dataR.GetString(4);
                        
                        var trip = FindOneTrip(tripId);
                        
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

        public void Save(Booking entity)
        {
            Logger.InfoFormat("Entering Save with value {0}", entity);
            
            var con = DatabaseUtils.GetConnection();

            try
            {
                _validator.Validate(entity);
            }
            catch (ValidationException)
            {
                Logger.ErrorFormat("Invalid booking {0}", entity);
            }
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Bookings(tripId, numberSeats, clientFirstName, clientLastName)  values (@trip, @seats, @first, @last)";

                var paramTrip = comm.CreateParameter();
                paramTrip.ParameterName = "@trip";
                paramTrip.Value = entity.Trip.Id;
                comm.Parameters.Add(paramTrip);
                
                var paramSeats = comm.CreateParameter();
                paramSeats.ParameterName = "@seats";
                paramSeats.Value = entity.NrSeats;
                comm.Parameters.Add(paramSeats);
                
                var paramClientF = comm.CreateParameter();
                paramClientF.ParameterName = "@first";
                paramClientF.Value = entity.ClientFirstName;
                comm.Parameters.Add(paramClientF);
                
                var paramClientL = comm.CreateParameter();
                paramClientL.ParameterName = "@last";
                paramClientL.Value = entity.ClientLastName;
                comm.Parameters.Add(paramClientL);
                
                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    Logger.ErrorFormat("Booking {0} not saved", entity);
                    throw new Exception("No booking added !");
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
                comm.CommandText = "delete from Bookings where id=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                
                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0)
                {
                    Logger.ErrorFormat("Booking with id {0} not deleted", id);
                    throw new Exception("No booking deleted!");
                }
            }
            
            Logger.Info("Exiting Delete");
        }

        public void Update(Booking entity)
        {
            throw new System.NotImplementedException();
        }

        public Trip FindOneTrip(long tripId)
        {
            Logger.InfoFormat("Entering FindOne with value {0}", tripId);

            var con = DbUtils.DatabaseUtils.GetConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Trips where id=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = tripId;
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
                        trip.Id = tripId;
                        
                        Logger.InfoFormat("Exiting FindOne with value {0}", trip);
                        return trip;
                    }
                }
            }
            Logger.InfoFormat("Exiting FindOne with value {0}", null);
            
            return null;
        }
        
    }
}