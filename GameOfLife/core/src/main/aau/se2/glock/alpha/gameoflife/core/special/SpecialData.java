package aau.se2.glock.alpha.gameoflife.core.special;

public class SpecialData {

    public Car selectCar(CarType type){
        if(type == CarType.HATCHBACK){
            return new Car(10000,100,type);
        }else{
            return new Car(50000,200,type);
        }
    }

    public Building selectHouse(BuildingType type){
        if(type == BuildingType.SINGLEHOUSE){
            return new Building(200000,type);
        }else if(type == BuildingType.FAMILYHOUSE){
            return new Building(500000,type);
        }else{
            return new Building(1000000,type);
        }
    }

}
