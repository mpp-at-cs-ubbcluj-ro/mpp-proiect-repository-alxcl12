using System;

namespace Lab2C.Model
{
    /// <summary>
    /// Class used to model a client
    /// </summary>
    public class Client: Entity<long>
    {
        private String FirstName { get; set; }
        private String LastName { get; set; }

        public Client(string firstName, string lastName)
        {
            FirstName = firstName;
            LastName = lastName;
        }
    }
}