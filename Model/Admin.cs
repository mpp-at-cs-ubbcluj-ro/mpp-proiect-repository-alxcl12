using System;

namespace Lab2C.Model
{
    /// <summary>
    /// Class used to model an administrator to the application
    /// </summary>
    public class Admin: Entity<long>
    {
        public String Username { get; set; }
        public byte[] PasswordHash { get; set; }

        public Admin(string username, byte[] passwordHash)
        {
            Username = username;
            PasswordHash = passwordHash;
        }
        public override string ToString()
        {
            return "Admin: " + Username + "  PassHash: " + PasswordHash + "\n";
        }
    }
}