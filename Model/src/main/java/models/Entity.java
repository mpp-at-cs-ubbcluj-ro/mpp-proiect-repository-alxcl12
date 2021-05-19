package models;/*
 *  @author albua
 *  created on 28/02/2021
 */

/**
 * Generic class used to represent entities with an unique identification
 * @param <Type> type ID of entity
 */
public class Entity<Type> {
    protected Type ID;

    public Type getID() {
        return ID;
    }

    public void setID(Type ID) {
        this.ID = ID;
    }
}
