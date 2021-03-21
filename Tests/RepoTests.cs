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
        private TripRepository _tripRepo;
        private BookingRepository _bookingRepo;
        
        private AdminValidator _adminValidator;
        private TripValidator _tripValidator;
        private BookingValidator _bookingValidator;
        
        [SetUp]
        public void Setup()
        {
            _adminValidator = new AdminValidator();
            _tripValidator = new TripValidator();
            _bookingValidator = new BookingValidator();
            
            _adminRepo = new AdminRepository(_adminValidator);
            _tripRepo = new TripRepository(_tripValidator);
            _bookingRepo = new BookingRepository(_bookingValidator);
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
        
    }
}