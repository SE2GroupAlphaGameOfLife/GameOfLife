package aau.se2.glock.alpha.gameoflife.core.special;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import aau.se2.glock.alpha.gameoflife.core.special.Building;
import aau.se2.glock.alpha.gameoflife.core.special.BuildingType;

public class BuildingTest {
    @Test
    public void testGetPrice() {
        // Arrange
        int price = 100;
        Building building = new Building(price, BuildingType.SINGLEHOUSE);

        // Act
        int result = building.getPrice();

        // Assert
        assertEquals(price, result);
    }

    @Test
    public void testSetPrice() {
        // Arrange
        int initialPrice = 100;
        Building building = new Building(initialPrice, BuildingType.SINGLEHOUSE);
        int newPrice = 200;

        // Act
        building.setPrice(newPrice);
        int updatedPrice = building.getPrice();

        // Assert
        assertEquals(newPrice, updatedPrice);
    }

    @Test
    public void testGetType() {
        // Arrange
        BuildingType type = BuildingType.SINGLEHOUSE;
        Building building = new Building(100, type);

        // Act
        BuildingType result = building.getType();

        // Assert
        assertEquals(type, result);
    }

    @Test
    public void testSetType() {
        // Arrange
        BuildingType initialType = BuildingType.SINGLEHOUSE;
        Building building = new Building(100, initialType);
        BuildingType newType = BuildingType.FAMILYHOUSE;

        // Act
        building.setType(newType);
        BuildingType updatedType = building.getType();

        // Assert
        assertEquals(newType, updatedType);
    }

}
