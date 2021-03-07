using System;

namespace Lab2C.Model.Validators
{
    /// <summary>
    /// Custom exception used to signal validation exception
    /// </summary>
    public class ValidationException: ApplicationException
    {
        public ValidationException(String message) : base(message){}
    }
}