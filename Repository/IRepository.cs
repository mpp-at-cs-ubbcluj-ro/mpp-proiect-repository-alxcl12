using System.Collections.Generic;
using Lab2C.Model;

namespace Lab2C.Repository
{
    /// <summary>
    /// Default interface used to store an entity with CRUD operations
    /// </summary>
    /// <typeparam name="ID"> id of entity to be stored </typeparam>
    /// <typeparam name="TE"> stored entity </typeparam>
    public interface IRepository<ID, TE> where TE: Entity<ID>
    {
        TE FindOne(ID id);

        IEnumerable<TE> FindAll();

        TE Save(TE entity);

        TE Delete(ID id);

        TE Update(TE entity);
    }
}