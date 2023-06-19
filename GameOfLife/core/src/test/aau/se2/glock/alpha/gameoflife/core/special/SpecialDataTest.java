package aau.se2.glock.alpha.gameoflife.core.special;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SpecialDataTest {

    private SpecialData data;
    private Car sportscar;
    private Car hatchback;
    private Building singlehouse;
    private Building familyhouse;
    private Building villa;

    @Before
    public void setup() {
        data = new SpecialData();
        sportscar = new Car(50000, 200, CarType.SPORTSCAR);
        hatchback = new Car(10000, 100, CarType.HATCHBACK);

        singlehouse = new Building(200000, BuildingType.SINGLEHOUSE);
        familyhouse = new Building(500000, BuildingType.FAMILYHOUSE);
        villa = new Building(1000000, BuildingType.VILLA);
    }

    @Test
    public void testSelectCar() {
        assertEquals(hatchback.getType(), data.selectCar(CarType.HATCHBACK).getType());
        assertEquals(sportscar.getType(), data.selectCar(CarType.SPORTSCAR).getType());

        assertEquals(hatchback.getPrice(), data.selectCar(CarType.HATCHBACK).getPrice());
        assertEquals(sportscar.getPrice(), data.selectCar(CarType.SPORTSCAR).getPrice());

        assertEquals(hatchback.getLp(), data.selectCar(CarType.HATCHBACK).getLp());
        assertEquals(sportscar.getLp(), data.selectCar(CarType.SPORTSCAR).getLp());
    }

    @Test
    public void testSelectHouse() {
        assertEquals(singlehouse.getType(), data.selectHouse(BuildingType.SINGLEHOUSE).getType());
        assertEquals(familyhouse.getType(), data.selectHouse(BuildingType.FAMILYHOUSE).getType());
        assertEquals(villa.getType(), data.selectHouse(BuildingType.VILLA).getType());

        assertEquals(singlehouse.getPrice(), data.selectHouse(BuildingType.SINGLEHOUSE).getPrice());
        assertEquals(familyhouse.getPrice(), data.selectHouse(BuildingType.FAMILYHOUSE).getPrice());
        assertEquals(villa.getPrice(), data.selectHouse(BuildingType.VILLA).getPrice());
    }
}
