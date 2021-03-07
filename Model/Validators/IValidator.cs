namespace Lab2C.Model.Validators
{
    /// <summary>
    /// Interface used to implement classes that validate different entities
    /// </summary>
    /// <typeparam name="T"> type of entity </typeparam>
    public interface IValidator<T>
    {
        void Validate(T element);
    }
}