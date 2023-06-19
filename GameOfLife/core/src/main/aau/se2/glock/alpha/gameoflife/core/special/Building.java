package aau.se2.glock.alpha.gameoflife.core.special;

public class Building {
    private int price;
    private BuildingType type;

    public Building() {

    }
    public Building(int price, BuildingType type) {
        this.price = price;
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public BuildingType getType() {
        return type;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Preis: " + price +
                "\n typ: " + type;
    }
}
