package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.logic.SpecialEvent;

public class SpecialEventTest {

    private SpecialEvent event;


    @Before
    public void setUp() {
        event = new SpecialEvent();
        GameOfLife.self = new Player();
    }

    @Test
    public void testEventOptionAGet15LP() {
        SpecialEvent event = new SpecialEvent("get15tLP", 1500, 0, null, null, null);
        String result = event.eventOptionA();
        assertEquals("Du erhälst 1500LP!", result);
    }

    @Test
    public void testEventOptionAPay20t() {
        SpecialEvent event = new SpecialEvent("pay20t", 0, -20000, null, null, null);
        String result = event.eventOptionA();
        assertEquals("Du verlierst -20000€!", result);
    }

    @Test
    public void testEventOptionAHouse() {
        SpecialEvent event = new SpecialEvent("house", 0, 0, null, null, null);
        String result = event.eventOptionA();
        assertEquals("buyHouse", result);
    }

    @Test
    public void testEventOptionACar() {
        SpecialEvent event = new SpecialEvent("car", 0, 0, null, null, null);
        String result = event.eventOptionA();
        assertEquals("buyCar", result);
    }

    @Test
    public void testEventOptionAGet3tLP() {
        SpecialEvent event = new SpecialEvent("get3tLP", 3000, 0, null, null, null);
        String result = event.eventOptionA();
        assertEquals("Du erhälst 3000LP!", result);
    }

    @Test
    public void testEventOptionAGet3_5() {
        SpecialEvent event = new SpecialEvent("get3.5", 3500, 0, null, null, null);
        String result = event.eventOptionA();
        assertEquals("Du erhälst 3500LP!", result);
    }

    @Test
    public void testEventOptionAPay10tEUR() {
        SpecialEvent event = new SpecialEvent("pay10tEUR", 0, -10000, null, null, null);
        String result = event.eventOptionA();
        assertEquals("Du verlierst -10000€!", result);
    }

    @Test
    public void testEventOptionAGet200tEUR() {
        SpecialEvent event = new SpecialEvent("get200tEUR", 0, 200000, null, null, null);
        String result = event.eventOptionA();
        assertEquals("Du erhälst 200000€!", result);
    }

    @Test
    public void testEventOptionADiploma() {
        SpecialEvent event = new SpecialEvent("diploma", 0, 0, null, null, null);
        String result = event.eventOptionA();
        assertEquals("Intelligenz+", result);
    }

    @Test
    public void testEventOptionADoctor() {
        SpecialEvent event = new SpecialEvent("doctor", 0, 0, null, null, null);
        String result = event.eventOptionA();
        assertEquals("Intelligenz++", result);
    }

    @Test
    public void testEventOptionANewCompany() {
        SpecialEvent event = new SpecialEvent("newcompany", 0, 0, null, null, null);
        String result = event.eventOptionA();
        assertEquals("neue FIRMA?", result);
    }

    @Test
    public void testEventOptionAShares100tEUR() {
        SpecialEvent event = new SpecialEvent("shares100tEUR", 0, 100000, null, null, null);
        String result = event.eventOptionA();
        assertEquals("Du erhälst 100000€!", result);
    }

    @Test
    public void testEventOptionAUnknownEvent() {
        SpecialEvent event = new SpecialEvent("unknown", 0, 0, null, null, null);
        String result = event.eventOptionA();
        assertEquals("Event not implemented", result);
    }

    @Test
    public void testEventOptionBGet15LP() {
        SpecialEvent event = new SpecialEvent("get15tLP", 1500, 0, null, null, null);
        String result = event.eventOptionB();
        assertEquals("Du erhälst 1500LP!", result);
    }

    @Test
    public void testEventOptionBPay20t() {
        SpecialEvent event = new SpecialEvent("pay20t", 0, -20000, null, null, null);
        String result = event.eventOptionB();
        assertEquals("Du verlierst -20000€!", result);
    }

    @Test
    public void testEventOptionBHouse() {
        SpecialEvent event = new SpecialEvent("house", 0, 0, null, null, null);
        String result = event.eventOptionB();
        assertEquals("Du entscheidest dich kein Haus zu kaufen", result);
    }

    @Test
    public void testEventOptionBCar() {
        SpecialEvent event = new SpecialEvent("car", 0, 0, null, null, null);
        String result = event.eventOptionB();
        assertEquals("Du entscheidest dich kein Auto zu kaufen", result);
    }

    @Test
    public void testEventOptionBLottery() {
        SpecialEvent event = new SpecialEvent("lottery", 0, 0, null, null, null);
        String result = event.eventOptionB();
        assertEquals("Du entscheidest dich kein Los zu kaufen", result);
    }

    @Test
    public void testEventOptionBCasino() {
        SpecialEvent event = new SpecialEvent("casino", 0, 0, null, null, null);
        String result = event.eventOptionB();
        assertEquals("Du entscheidest gehst lieber kein Risiko ein", result);
    }

    @Test
    public void testEventOptionBGet3tLP() {
        SpecialEvent event = new SpecialEvent("get3tLP", 3000, 0, null, null, null);
        String result = event.eventOptionB();
        assertEquals("Du erhälst 3000LP!", result);
    }

    @Test
    public void testEventOptionBGet3_5() {
        SpecialEvent event = new SpecialEvent("get3.5", 3500, 0, null, null, null);
        String result = event.eventOptionB();
        assertEquals("Du erhälst 3500LP!", result);
    }

    @Test
    public void testEventOptionBPay10tEUR() {
        SpecialEvent event = new SpecialEvent("pay10tEUR", 0, -10000, null, null, null);
        String result = event.eventOptionB();
        assertEquals("Du verlierst -10000€!", result);
    }

    @Test
    public void testEventOptionBGet200tEUR() {
        SpecialEvent event = new SpecialEvent("get200tEUR", 0, 200000, null, null, null);
        String result = event.eventOptionB();
        assertEquals("Du erhälst 200000€!", result);
    }

    @Test
    public void testEventOptionBDiploma() {
        SpecialEvent event = new SpecialEvent("diploma", 0, 0, null, null, null);
        String result = event.eventOptionB();
        assertEquals("Intelligenz+", result);
    }

    @Test
    public void testEventOptionBDoctor() {
        SpecialEvent event = new SpecialEvent("doctor", 0, 0, null, null, null);
        String result = event.eventOptionB();
        assertEquals("Intelligenz++", result);
    }

    @Test
    public void testEventOptionBNewCompany() {
        SpecialEvent event = new SpecialEvent("newcompany", 0, 0, null, null, null);
        String result = event.eventOptionB();
        assertEquals("neue FIRMA?", result);
    }

    @Test
    public void testEventOptionBShares100tEUR() {
        SpecialEvent event = new SpecialEvent("shares100tEUR", 0, 100000, null, null, null);
        String result = event.eventOptionB();
        assertEquals("Du erhälst 100000€!", result);
    }

    @Test
    public void testEventOptionBUnknownEvent() {
        SpecialEvent event = new SpecialEvent("unknown", 0, 0, null, null, null);
        String result = event.eventOptionB();
        assertEquals("Event not implemented", result);
    }

    @Test
    public void testGetText() {
        event = new SpecialEvent("test", 0, 0, "Option A", "Option B", "Test Message");
        String result = event.getText();
        assertEquals("Test Message", result);
    }

    @Test
    public void testGetLp() {
        event = new SpecialEvent("test", 10, 0, "Option A", "Option B", "Test Message");
        int result = event.getLp();
        assertEquals(10, result);
    }

    @Test
    public void testGetCash() {
        event = new SpecialEvent("test", 0, 50, "Option A", "Option B", "Test Message");
        int result = event.getCash();
        assertEquals(50, result);
    }

    @Test
    public void testGetMessage() {
        event = new SpecialEvent("test", 0, 0, "Option A", "Option B", "Test Message");
        String result = event.getMessage();
        assertEquals("Test Message", result);
    }

    @Test
    public void testGetOptionA() {
        event = new SpecialEvent("test", 0, 0, "Option A", "Option B", "Test Message");
        String result = event.getOptionA();
        assertEquals("Option A", result);
    }

    @Test
    public void testGetOptionB() {
        event = new SpecialEvent("test", 0, 0, "Option A", "Option B", "Test Message");
        String result = event.getOptionB();
        assertEquals("Option B", result);
    }

    @Test
    public void testGetType() {
        event = new SpecialEvent("test", 0, 0, "Option A", "Option B", "Test Message");
        String result = event.getType();
        assertEquals("test", result);
    }

    @Test
    public void testToString() {
        event = new SpecialEvent("test", 0, 0, "Option A", "Option B", "Test Message");
        String result = event.toString();
        assertEquals("SpecialEvent{message='Test Message', type='test', lp=0, cash=0}", result);
    }
}

