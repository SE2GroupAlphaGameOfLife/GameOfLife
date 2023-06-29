package aau.se2.glock.alpha.gameoflife.core.logic;

import com.badlogic.gdx.Game;

import java.security.SecureRandom;
import java.util.Random;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.core.jobs.Job;

public class EventFunctions {

    public static String evAddLP(int amount) {
        GameOfLife.self.changeBalance(0, amount);
        return ("Du erhälst " + amount + "LP!");
    }

    public static String evPayMoney(int amount) {
        GameOfLife.self.changeBalance(amount, 0);
        return ("Du verlierst " + -amount + "€!");
    }

    public static String evGetMoney(int amount) {
        GameOfLife.self.changeBalance(amount, 0);
        return ("Du erhälst " + amount + "€!");
    }

    public static String evBuyCar() {
        return ("buyCar");
    }

    public static String evBuyHouse() {
        return ("buyHouse");
    }

    public static String evPromotion(int amount) {
        Job currentJob = GameOfLife.self.getCurrentJob();
        for (int i = 0; i < amount; i++) {
            try {
                currentJob.befoerderung();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return ("Du wurdest befördert");
    }

    public static String evCasino() {
        Player player = GameOfLife.self;
        int money = player.getMoney();
        if (money < 0) {
            return "Du kannst nicht ins Kasino wenn du Schulden hast";
        }
        int gamblingResult = gamble();
        if (gamblingResult == 0) {
            player.changeBalance(-money, 0);
            return ("Du verspielst dein ganzes Geld!!");
        }
        return ("");
    }

    public static String evLottery() {
        Player player = GameOfLife.self;
        player.changeBalance(-1000, 0);
        int gamblingResult = gamble();
        if (gamblingResult < 5) {
            return "Leider eine Niete";
        } else if (gamblingResult < 9) {
            int winAmount = 20 * (gamblingResult);
            player.changeBalance(winAmount, 0);
            return "Wenigstens etwas, du erhälst einen Teilgewinn von " + winAmount + "€";
        } else {
            player.changeBalance(100000, 0);
            return "Du erhlälst den Hauptgewinn von 100000";
        }
    }

    public static String evDiploma() {
        GameOfLife.self.setDiploma(true);
        return "Intelligenz+";
    }

    public static String evDoctor() {
        GameOfLife.self.setDoctor(true);
        return "Intelligenz++";
    }

    public static String evNewCompany() {
        return "neue FIRMA?";
    }

    public static String evWedding(){
        GameOfLife.self.setMarried(true);
        //2000, da direkt nach der Hochzeit die Runde berechnet wird und somit 1500 + 2000 = 3500
        GameOfLife.self.changeBalance(0,2000);
        return "Willst du?";
    }

    public static String evChild(){
        GameOfLife.self.setChildren(GameOfLife.self.getChildren()+1);
        //Pro Kind 350LP
        //GameOfLife.self.changeBalance(0,350);
        if(GameOfLife.self.getChildren()<=1){
            return "Du hast ein Kind bekommen!";
        }else{
            return "Du hast ein weiteres Kind bekommen!";
        }
    }

    public static String evTwins(){
        GameOfLife.self.setChildren(GameOfLife.self.getChildren()+2);
        //Pro Kind 350LP
        //GameOfLife.self.changeBalance(0,700);
        return "Du hast Zwillinge bekommen!";
    }

    public static String evCareer() {
        return ("changeCareer");
    }

    private static int gamble() {
        Random random = new SecureRandom();
        return random.nextInt(10);

    }


}
