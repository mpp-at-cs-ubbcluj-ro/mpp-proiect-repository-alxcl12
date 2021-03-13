using System;
using System.Linq;
using Lab2C.Model;
using Lab2C.Model.Validators;
using Lab2C.Repository;
using NUnit.Framework;

namespace ProjectTests
{
    public class RepositoryTests
    {
        private AdminRepository _adminRepo;
        private AdminValidator _adminValidator;
        
        [SetUp]
        public void Setup()
        {
            _adminValidator = new AdminValidator();


            _adminRepo = new AdminRepository(_adminValidator);
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
    }
}