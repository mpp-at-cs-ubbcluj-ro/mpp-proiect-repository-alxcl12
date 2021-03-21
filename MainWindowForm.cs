using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Lab2C.Service;

namespace Lab2C
{
    public partial class MainWindowForm : Form
    {
        private Service.Service service;
        private LoginForm loginForm;

        public MainWindowForm()
        {
            InitializeComponent();
        }

        internal void Set(Service.Service service, LoginForm logonForm)
        {
            this.service = service;
            this.loginForm = logonForm;
            LoadData();
        }

        private void LoadData()
        {
            dataGridTrips.DataSource = service.GetAllTrips();
        }

        private void Logout(object sender, EventArgs e)
        {
            this.Hide();
            loginForm.Show();
        }

        private void displayBookings(object sender, DataGridViewCellEventArgs e)
        {
            long tripId = (long)dataGridTrips.CurrentRow.Cells["ID"].Value;
            //long tripId = (long)row.Cells["ID"].Value;
            dataGridPass.Rows.Clear();

            dataGridPass.ColumnCount = 2;
            dataGridPass.Columns[0].Name = "Seat number";
            dataGridPass.Columns[1].Name = "Client";

            var bookings = service.GetBookingsForTrip(tripId);
            var bookingsList = bookings.ToList();

            int maxi = 18;
            int k = 1;
            for(int i = 0; i < maxi; i++)
            {
                if(i >= bookings.Count())
                {
                    while(k<=18)
                    {
                        string[] row = new string[] { k.ToString(), "-" };
                        dataGridPass.Rows.Add(row);
                        k++;
                    }
                    break;
                }
                else
                {
                    int j = 0;
                    for (j = 0; j < bookingsList[i].NrSeats; j++)
                    {
                        string[] row = new string[] { k.ToString(), bookingsList[i].ClientFirstName + bookingsList[i].ClientLastName };
                        dataGridPass.Rows.Add(row);
                        k++;
                    }
                    maxi -= j +1;
                }
            }
        }

        private void buttonBook_Click(object sender, EventArgs e)
        {
            string firstName = textBoxFirst.Text;
            string lastName = textBoxLast.Text;
            string nr = textBoxSeats.Text;

            service.AddBooking((long)dataGridTrips.CurrentRow.Cells["ID"].Value, firstName, lastName, Int32.Parse(nr));

            textBoxFirst.Clear();
            textBoxLast.Clear();
            textBoxSeats.Clear();

            LoadData();
        }

        private void buttonFilter_Click(object sender, EventArgs e)
        {
            string source = textBoxSource.Text;
            DateTime date = dateTimePicker.Value;

            var toFill = service.GetTripsBySourceAndDate(source, date);

            dataGridTrips.DataSource = toFill;

            textBoxSource.Clear();

        }

        private void buttonReset_Click(object sender, EventArgs e)
        {
            LoadData();
        }
    }
}
