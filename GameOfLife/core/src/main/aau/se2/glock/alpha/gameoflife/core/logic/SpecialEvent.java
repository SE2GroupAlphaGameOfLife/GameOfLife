package aau.se2.glock.alpha.gameoflife.core.logic;

/**
 *
 */
public class SpecialEvent implements Event {

    private String message;

    private String type;

    private int lp;

    private int cash;

    private String optionA;

    private String optionB;

    public SpecialEvent(){
    }

    public SpecialEvent(String type) {
        this.lp = 0;
        this.cash = 0;
        this.type = type;
        //setBasicStats();
    }

    public SpecialEvent(String type, int lp, int cash, String optionA, String optionB,String message) {
        this.message = message;
        this.type = type;
        this.lp = lp;
        this.cash = cash;
        this.optionA = optionA;
        this.optionB = optionB;
    }
/*
    private void setBasicStats() {
        switch (type) {
            case "get15tLP":
                this.message = "Du gehst Bungee-Springen und erhälst 1.500LP";
                this. lp =1500;
                break;
            case "car":
                this.message = "Du hast die Möglichkeit ein Auto zu kaufen";
                break;
            case "house":
                this.message = "Du hast die Möglichkeit ein Haus zu kaufen";
                break;
            case "lottery":
                this.message = "Du spielst Lotto";
                break;
            case "casino":
                this.message = "Du gehst ins Casino";
                break;
            case "get3tLP":
                this.message = "Du gehst mit Delfinen schwimmen:3.000LP";
                this.lp = 3000;
                break;
            case "pay20t":
                    this.message = "STUDIENGEBÜHREN 20.000 LOL";
                    this.cash=-20000;
                    break;

        }

    }*/
    public void eventOptionA(){
        //TODO Switch case and call appropriate method

    }

    public void eventOptionB(){
        //TODO Switch case and call appropriate method
    }

    @Override
    public String getText() {
        return this.message;
    }

    @Override
    public int getLp() {
        return this.lp;
    }

    @Override
    public int getCash() {
        return this.cash;
    }

    public String getMessage() {
        return message;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getType(){
        return this.type;
    }

    @Override
    public String toString() {
        return "SpecialEvent{" +
                "message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", lp=" + lp +
                ", cash=" + cash +
                '}';
    }
}

