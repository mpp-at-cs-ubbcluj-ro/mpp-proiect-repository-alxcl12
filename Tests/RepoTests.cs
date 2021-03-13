using System;
using System.Linq;
using Lab2C.Model;
using Lab2C.Model.Validators;
using Lab2C.Repository;
using NUnit.Framework;

namespace Lab2C.Tests
{
    public class RepositoryTests
    {
        private AdminRepository _adminRepo;
        private ClientRepository _clientRepo;
        private TripRepository _tripRepo;
        private BookingRepository _bookingRepo;
        
        private AdminValidator _adminValidator;
        private ClientValidator _clientValidator;
        private TripValidator _tripValidator;
        private BookingValidator _bookingValidator;
        
        [SetUp]
        public void Setup()
        {
            _adminValidator = new AdminValidator();
            _clientValidator = new ClientValidator();
            _tripValidator = new TripValidator();
            _bookingValidator = new BookingValidator();
            
            _adminRepo = new AdminRepository(_adminValidator);
            _clientRepo = new ClientRepository(_clientValidator);
            _tripRepo = new TripRepository(_tripValidator);
            _bookingRepo = new BookingRepository(_bookingValidator);
        }

        [Test]
        public void AdminRepoTest()
        {
            var random = new Random();
            var number = random.Next();
            
            var testAdmin = new Admin("TestAdminUsername" + number, "TestPasswordHash" + number);

            _adminRepo.Save(testAdmin);

            var admins = _adminRepo.FindAll();

            Assert.AreNotEqual(0, admins.Count());
            
            Admin recover = _adminRepo.FindOne(1);

            Assert.AreEqual("TestAdminUsername-919258746", recover.Username);
        }
        
        [Test]
        public void ClientRepoTest()
        {
            var random = new Random();
            var number = random.Next();
            
            var testClient = new Client("TestClientFirstName" + number, "TestClientLastName" + number);

            _clientRepo.Save(testClient);

            var clients = _clientRepo.FindAll();

            Assert.AreNotEqual(0, clients.Count());
            
            Client recover = _clientRepo.FindOne(1);

            Assert.AreEqual("TestClientFirstName-2045517797", recover.FirstName);
        }
        
        [Test]
        public void TripRepoTest()
        {
            var random = new Random();
            var number = random.Next();
            
            var testTrip = new Trip("TestSource" + number, "TestDestination" + number,
                DateTime.Now, 5);

            _tripRepo.Save(testTrip);

            var trips = _tripRepo.FindAll();

            Assert.AreNotEqual(0, trips.Count());
            
            Trip recover = _tripRepo.FindOne(1);

            Assert.AreEqual("TestSource-206989128", recover.Source);
        }
        
        public void BookingRepoTest()
        {
            var random = new Random();
            var number = random.Next();

            var testClient = new Client("tst", "test");
            testClient.Id = 1;

            var testTrip = new Trip("tst", "test", DateTime.Now, 5);
            testTrip.Id = 1;

            var testBooking = new Booking(testClient, testTrip, 3);
            
            _bookingRepo.Save(testBooking);

            var bookings = _bookingRepo.FindAll();

            Assert.AreNotEqual(0, bookings.Count());
            
            Booking recover = _bookingRepo.FindOne(1);

            Assert.AreEqual(3, recover.NrSeats);
        }
    }
}