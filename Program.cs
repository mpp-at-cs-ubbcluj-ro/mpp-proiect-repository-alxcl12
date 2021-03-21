using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Lab2C.Service;
using Lab2C.Model.Validators;
using Lab2C.Repository;
using Lab2C.Model;

namespace Lab2C
{
    class Program
    {
        [STAThread]
        static void Main(string[] args)
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            BookingValidator bookingValidator = new BookingValidator();
            TripValidator tripValidator = new TripValidator();
            AdminValidator adminValidator = new AdminValidator();

            AdminRepository adminRepository = new AdminRepository(adminValidator);
            TripRepository tripRepository = new TripRepository(tripValidator);
            BookingRepository bookingRepository = new BookingRepository(bookingValidator);
;

            Service.Service service = new Service.Service(adminRepository, bookingRepository, tripRepository);

            service.GetAllTrips();

            LoginForm logonForm = new LoginForm();
            MainWindowForm mainWindowForm = new MainWindowForm();

            logonForm.Set(service, mainWindowForm);
            mainWindowForm.Set(service, logonForm);

            //Trip trip = new Trip("Milano", "Londra", DateTime.Now, 18);
            //tripRepository.Save(trip);

            Application.Run(logonForm);
        }
    }
}
