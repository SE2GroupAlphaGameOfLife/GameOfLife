package aau.se2.glock.alpha.gameoflife.core.logic;

public class SpecialEvent implements Event {

    private static final String GET_15_LP = "get15tLP";
    private static final String PAY_20T = "pay20t";
    private static final String HOUSE = "house";
    private static final String CAR = "car";
    private static final String LOTTERY = "lottery";
    private static final String CASINO = "casino";
    private static final String GET_3T_LP = "get3tLP";
    private static final String GET_3_5 = "get3.5";
    private static final String PAY_10T_EUR = "pay10tEUR";
    private static final String GET_200T_EUR = "get200tEUR";
    private static final String DIPLOMA = "diploma";
    private static final String DOCTOR = "doctor";
    private static final String PROMOTION = "promotion";
    private static final String NEW_COMPANY = "newcompany";
    private static final String PROMOTION_2X = "promotion2x";
    private static final String SHARES_100T_EUR = "shares100tEUR";
    private static final String EVENT_NOT_IMPLEMENTED = "Event not implemented";
    private static final String CHANGE_CAREER = "changecareer";

    private String message;
    private String type;
    private int lp;
    private int cash;
    private String optionA;
    private String optionB;

    public SpecialEvent(){
    }

    public SpecialEvent(String type) {
        this(type, 0, 0, null, null, null);
    }

    public SpecialEvent(String type, int lp, int cash, String optionA, String optionB, String message) {
        this.message = message;
        this.type = type;
        this.lp = lp;
        this.cash = cash;
        this.optionA = optionA;
        this.optionB = optionB;
    }

    public String eventOptionA() {
        switch (this.type) {
            case GET_15_LP:
            case GET_3T_LP:
            case GET_3_5:
                return EventFunctions.evAddLP(this.lp);
            case PAY_20T:
            case PAY_10T_EUR:
                return EventFunctions.evPayMoney(this.cash);
            case HOUSE:
                return EventFunctions.evBuyHouse();
            case CAR:
                return EventFunctions.evBuyCar();
            case LOTTERY:
                return EventFunctions.evLottery();
            case CASINO:
                return EventFunctions.evCasino();
            case GET_200T_EUR:
            case SHARES_100T_EUR:
                return EventFunctions.evGetMoney(this.cash);
            case DIPLOMA:
                return EventFunctions.evDiploma();
            case DOCTOR:
                return EventFunctions.evDoctor();
            case PROMOTION:
                return EventFunctions.evPromotion(1);
            case NEW_COMPANY:
                return EventFunctions.evNewCompany();
            case PROMOTION_2X:
                return EventFunctions.evPromotion(2);
            case CHANGE_CAREER:
                return EventFunctions.evCareer();
            default:
                return EVENT_NOT_IMPLEMENTED;
        }
    }

    public String eventOptionB() {
        switch (this.type) {
            case GET_15_LP:
            case GET_3_5:
            case GET_3T_LP:
                return EventFunctions.evAddLP(this.lp);
            case PAY_20T:
            case PAY_10T_EUR:
                return EventFunctions.evPayMoney(this.cash);
            case HOUSE:
                return "Du entscheidest dich kein Haus zu kaufen";
            case CAR:
                return "Du entscheidest dich kein Auto zu kaufen";
            case LOTTERY:
                return "Du entscheidest dich kein Los zu kaufen";
            case CASINO:
                return "Du entscheidest gehst lieber kein Risiko ein";
            case GET_200T_EUR:
            case SHARES_100T_EUR:
                return EventFunctions.evGetMoney(this.cash);
            case DIPLOMA:
                return EventFunctions.evDiploma();
            case DOCTOR:
                return EventFunctions.evDoctor();
            case PROMOTION:
                return EventFunctions.evPromotion(1);
            case NEW_COMPANY:
                return EventFunctions.evNewCompany();
            case PROMOTION_2X:
                return EventFunctions.evPromotion(2);
            case CHANGE_CAREER:
                return "Du behälst deinen derzeitigen Job bei";
            default:
                return EVENT_NOT_IMPLEMENTED;
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

    public String getType() {
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
