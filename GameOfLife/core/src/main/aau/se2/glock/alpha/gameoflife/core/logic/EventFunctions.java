package aau.se2.glock.alpha.gameoflife.core.logic;

import java.security.SecureRandom;
import java.util.Random;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.core.jobs.Job;

public class EventFunctions {

    public static String evAddLP(int amount){
        GameOfLife.self.changeBalance(0,amount);
        return("Du erhälst"+amount+"LP!");
    }
    public static String evPayMoney(int amount){
        GameOfLife.self.changeBalance(-amount,0);
        return("Du verlierst"+amount+"€!");
    }

    public static String evGetMoney(int amount){
        GameOfLife.self.changeBalance(amount,0);
        return("Du erhälst"+amount+"€!");
    }

    public static String evBuyCar(){
        return("buyCar");
    }
    public static String  evBuyHouse(){
        return ("buyHouse");
    }
    public static String evPromotion(int amount){
        Job currentJob = GameOfLife.self.getCurrentJob();
        for(int i = 0;i<amount;i++){
            try {
                currentJob.befoerderung();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return("Du wurdest befördert");
    }
    public static String evCasino(){
        Player player = GameOfLife.self;
        int money = player.getMoney();
        int gamblingResult = gamble();
        if(gamblingResult==0){
            player.changeBalance(-money,0);
            return ("Du verspielst dein ganzes Geld!!");
        }
        return("");
    }
    public static String evLottery(){
        Player player = GameOfLife.self;
        player.changeBalance(-1000,0);
        int gamblingResult = gamble();
        if(gamblingResult<5){
            return "Leider eine Niete";
        }else if(gamblingResult<9){
            int winAmount = 20*(gamblingResult);
            player.changeBalance(winAmount,0);
            return "Wenigstens etwas, du erhälst einen Teilgewinn von "+winAmount+"€";
        }else{
            player.changeBalance(100000,0);
            return "Du erhlälst den Hauptgewinn von 100000";
        }
    }
    public static String evDiploma(){
        GameOfLife.self.setDiploma(true);
        return "Intelligenz+";
    }
    public static String evDoctor(){
        GameOfLife.self.setDoctor(true);
        return "Intelligenz++";
    }
    public static String evNewCompany(){
        return "neue FIRMA?";
    }
    public static String evtWedding(){
        return "Noch nich impementiert";
    }
    public static String evtChild(){
        return "Child";
    }

    public static String evCareer(){
        return ("changeCareer");
    }
    private static int gamble(){
        Random random = new SecureRandom();
        int randomNumber = random.nextInt(10); // Generates a random integer between 0 and 9
        return randomNumber;
    }


}
