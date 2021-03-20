package utils;/*
 *  @author albua
 *  created on 28/02/2021
 */

public interface Observable {
    void addObserver(Observer e);
    void removeObserver(Observer e);
    void notifyObservers();
}

