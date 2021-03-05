using System;

namespace Lab2C.Model
{
    public class Admin: Entity<long>
    {
        private String Username { get; set; }
        private String PasswordHash { get; set; }

        public Admin(string username, string passwordHash)
        {
            Username = username;
            PasswordHash = passwordHash;
        }
    }
}