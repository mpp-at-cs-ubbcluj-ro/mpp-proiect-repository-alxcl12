/*
 *  @author albua
 *  created on 28/02/2021
 */
package model;

/**
 * Generic class used to represent entities with an unique identification
 * @param <Type>
 */
public class Entity<Type> {
    Type ID;

    public Type getID() {
        return ID;
    }

    public void setID(Type ID) {
        this.ID = ID;
    }
}
