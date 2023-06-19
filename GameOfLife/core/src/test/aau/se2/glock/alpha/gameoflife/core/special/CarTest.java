package aau.se2.glock.alpha.gameoflife.core.special;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CarTest {

    int price = 50000;
    int lp = 1234;

    @Test
    public void testGetPrice() {
        // Arrange
        Car car = new Car(price, lp, CarType.HATCHBACK);

        // Act
        int result = car.getPrice();

        // Assert
        assertEquals(price, result);
    }

    @Test
    public void testSetPrice() {
        // Arrange
        Car car = new Car(price, lp, CarType.HATCHBACK);
        int newPrice = 60000;

        // Act
        car.setPrice(newPrice);
        int updatedPrice = car.getPrice();

        // Assert
        assertEquals(newPrice, updatedPrice);
    }

    @Test
    public void testGetLp() {
        // Arrange
        Car car = new Car(price, lp, CarType.HATCHBACK);

        // Act
        int result = car.getLp();

        // Assert
        assertEquals(lp, result);
    }

    @Test
    public void testSetLp() {
        // Arrange
        Car car = new Car(price, lp, CarType.HATCHBACK);
        int newLp = 5678;

        // Act
        car.setLp(newLp);
        int updatedLp = car.getLp();

        // Assert
        assertEquals(newLp, updatedLp);
    }

    @Test
    public void testGetType() {
        // Arrange
        CarType type = CarType.HATCHBACK;
        Car car = new Car(price, lp, type);

        // Act
        CarType result = car.getType();

        // Assert
        assertEquals(type, result);
    }

    @Test
    public void testSetType() {
        // Arrange
        CarType initialType = CarType.HATCHBACK;
        Car car = new Car(price, lp, initialType);
        CarType newType = CarType.SPORTSCAR;

        // Act
        car.setType(newType);
        CarType updatedType = car.getType();

        // Assert
        assertEquals(newType, updatedType);
    }

}
