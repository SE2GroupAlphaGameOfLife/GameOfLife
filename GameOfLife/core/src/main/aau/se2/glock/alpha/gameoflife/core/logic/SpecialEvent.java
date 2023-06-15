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

    public String eventOptionA(){
        switch (this.type){
            case "get15LP":
                return EventFunctions.evAddLP(this.lp);
            case "pay20t":
                return EventFunctions.evPayMoney(this.cash);
            case "house":
                return EventFunctions.evBuyHouse();
            case "car":
                return EventFunctions.evBuyCar();
            case "lottery":
                return EventFunctions.evLottery();
            case "casino":
                return EventFunctions.evCasino();
            case "get3tLP":
                return EventFunctions.evAddLP(3000);
            case "get3.5":
                return EventFunctions.evAddLP(3500);
            case "pay10tEUR":
                return EventFunctions.evPayMoney(10000);
            case "get200tEUR":
                return EventFunctions.evGetMoney(200000);
            case "diploma":
                return EventFunctions.evDiploma();
            case "doctor":
                return EventFunctions.evDoctor();
            case "promotion":
                return EventFunctions.evPromotion(1);
            case "newcompany":
                return EventFunctions.evNewCompany();
            case "promotion2x":
                return EventFunctions.evPromotion(2);
            case "shares100tEUR":
                return EventFunctions.evGetMoney(100000);
            default:
                return "Event not implemented";
        }

    }

    public String eventOptionB(){
        switch (this.type){
            case "get15LP":
                return EventFunctions.evAddLP(this.lp);
            case "pay20t":
                return EventFunctions.evPayMoney(this.cash);
            case "house":
                return "Du entscheidest dich kein Haus zu kaufen";
            case "car":
                return "Du entscheidest dich kein Auto zu kaufen";
            case "lottery":
                return "Du entscheidest dich kein Los zu kaufen";
            case "casino":
                return "Du entscheidest gehst lieber kein Risiko ein";
            case "get3tLP":
                return EventFunctions.evAddLP(3000);
            case "get3.5":
                return EventFunctions.evAddLP(3500);
            case "pay10tEUR":
                return EventFunctions.evPayMoney(10000);
            case "get200tEUR":
                return EventFunctions.evGetMoney(200000);
            case "diploma":
                return EventFunctions.evDiploma();
            case "doctor":
                return EventFunctions.evDoctor();
            case "promotion":
                return EventFunctions.evPromotion(1);
            case "newcompany":
                return EventFunctions.evNewCompany();
            case "promotion2x":
                return EventFunctions.evPromotion(2);
            case "shares100tEUR":
                return EventFunctions.evGetMoney(100000);
            default:
                return "Event not implemented";
        }
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

