package aau.se2.glock.alpha.gameoflife.networking.observer;

public interface ClientObserverSubject {
    void registerObserver(ClientObserver observer);

    void removeObserver(ClientObserver observer);

    void notifyObservers(String payload);
}
