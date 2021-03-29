public interface Observable {
    void addObserver(ObserverNormal o);
    void notifyAll(Object arg);
}
