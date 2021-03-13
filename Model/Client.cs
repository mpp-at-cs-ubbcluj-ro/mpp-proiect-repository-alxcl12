using System;

namespace Lab2C.Model
{
    /// <summary>
    /// Class used to model a client
    /// </summary>
    public class Client: Entity<long>
    {
        public String FirstName { get; set; }
        public String LastName { get; set; }

        public Client(string firstName, string lastName)
        {
            FirstName = firstName;
            LastName = lastName;
        }

        public override string ToString()
        {
            return "Client: " + FirstName + "  " + LastName + "\n";
        }
    }
}