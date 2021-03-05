package utils;/*
 *  @author albua
 *  created on 28/02/2021
 */

public interface Observable {
    void AddObserver(Observer e);
    void RemoveObserver(Observer e);
    void NotifyObservers();
}

