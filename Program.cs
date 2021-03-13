using System;
using Lab2C.Model.Validators;
using Lab2C.Repository;

namespace Lab2C
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            var validator = new AdminValidator();
            var adminRepository = new AdminRepository(validator);

            var list = adminRepository.FindAll();
            
            foreach (var admin in list)
            {
                Console.WriteLine(admin);
            }
        }
    }
}