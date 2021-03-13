using System;
using Lab2C.Model.Validators;
using Lab2C.Repository;
using Lab2C.Repository.DbUtils;

namespace Lab2C
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            var validator = new ClientValidator();
            var adminRepository = new ClientRepository(validator);

            var list = adminRepository.FindAll();
            
            foreach (var admin in list)
            {
                Console.WriteLine(admin);
            }
        }
    }
}