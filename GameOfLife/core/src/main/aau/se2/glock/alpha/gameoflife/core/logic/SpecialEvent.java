package aau.se2.glock.alpha.gameoflife.core.logic;

/**
 *
 */
public class SpecialEvent implements Event {

    private String message;

    private String type;

    private int lp;

    private int cash;

    public SpecialEvent(String type) {
        this.lp = 0;
        this.cash = 0;
        this.type = type;
        setBasicStats();
    }

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
        }

    }
    public void eventOptionA(){

    }

    public void eventOptionB(){

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
}
