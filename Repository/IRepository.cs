using System.Collections.Generic;
using Lab2C.Model;

namespace Lab2C.Repository
{
    public interface IRepository<ID, TE> where TE: Entity<ID>
    {
        TE FindOne(ID id);

        IEnumerable<TE> FindAll();

        TE Save(TE entity);

        TE Delete(ID id);

        TE Update(TE entity);
    }
}