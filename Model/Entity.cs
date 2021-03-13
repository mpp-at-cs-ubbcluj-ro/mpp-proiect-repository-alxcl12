namespace Lab2C.Model
{
    /// <summary>
    /// Generic class used to represent entities with an unique identification
    /// </summary>
    /// <typeparam name="TId"> type of ID </typeparam>
    public class Entity<TId>
    {
        public TId Id { get; set; }
    }
}