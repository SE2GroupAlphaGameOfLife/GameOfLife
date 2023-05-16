package aau.se2.glock.alpha.gameoflife.core.utilities;

public interface  ProximitySensorInterface {
    void registerSensor();
    void unregisterSensor();
    void setProximityListener(ProximityListener listener);
}
