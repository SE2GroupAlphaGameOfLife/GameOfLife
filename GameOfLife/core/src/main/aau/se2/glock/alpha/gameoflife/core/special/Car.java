package aau.se2.glock.alpha.gameoflife.core.special;

public class Car {
    private int price;
    private int lp;
    private CarType type;

    public Car(int price, int lp, CarType type) {
        this.price = price;
        this.lp = lp;
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public CarType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Car{" +
                "price=" + price +
                ", lp=" + lp +
                ", type=" + type +
                '}';
    }

    public void setType(CarType type) {
        this.type = type;
    }
}

