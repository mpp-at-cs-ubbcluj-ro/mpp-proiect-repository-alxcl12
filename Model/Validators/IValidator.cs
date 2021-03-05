namespace Lab2C.Model.Validators
{
    public interface IValidator<T>
    {
        void Validate(T element);
    }
}