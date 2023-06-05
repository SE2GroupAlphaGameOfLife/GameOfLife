package aau.se2.glock.alpha.gameoflife.networking.observers;

public interface ClientObserverSubject {
    void registerObserver(ClientObserver observer);

    void removeObserver(ClientObserver observer);

    void notifyObservers(String payload);
}
