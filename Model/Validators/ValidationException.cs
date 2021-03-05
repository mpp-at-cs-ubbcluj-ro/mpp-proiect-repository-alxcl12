using System;

namespace Lab2C.Model.Validators
{
    public class ValidationException: ApplicationException
    {
        public ValidationException(String message) : base(message){}
    }
}