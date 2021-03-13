using System;
using System.Linq;
using Lab2C.Model;
using Lab2C.Model.Validators;
using Lab2C.Repository;
using Lab2C.Repository.DbUtils;
using NUnit.Framework;

namespace Lab2C.Tests
{
    public class RepositoryTests
    {
        private AdminRepository _adminRepo;
        private ClientRepository _clientRepo;
        
        
        private AdminValidator _adminValidator;
        private ClientValidator _clientValidator;
        
        [SetUp]
        public void Setup()
        {
            _adminValidator = new AdminValidator();
            _clientValidator = new ClientValidator();

            _adminRepo = new AdminRepository(_adminValidator);
            _clientRepo = new ClientRepository(_clientValidator);
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
    }
}