using System.Windows.Forms;
using Lab2C.Service;

namespace Lab2C
{
    public partial class LoginForm : Form
    {
        private Service.Service service;
        private MainWindowForm mainWindowForm;

        public LoginForm()
        {
            InitializeComponent();
        }

        internal void Set(Service.Service service, MainWindowForm mainWindowForm)
        {
            this.service = service;
            this.mainWindowForm = mainWindowForm;
        }

        private void label1_Click(object sender, System.EventArgs e)
        {
            string username = textBoxUser.Text;
            string password = textBoxPass.Text;

            if(service.Login(username, password))
            {
                this.Hide();
                mainWindowForm.Show();
            }
            else
            {
                MessageBox.Show("Invalid credentials!");
            }
        }
    }
}