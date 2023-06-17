package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import aau.se2.glock.alpha.gameoflife.core.logic.SpecialEvent;

public class SpecialEventTest {

    SpecialEvent specialEvent;

    @Before
    public void setup(){
        specialEvent = new SpecialEvent("test",0,0,"test","test","test");

    }

    @Test
   public void testGetType(){
        assertEquals("test",specialEvent.getType());
    }
    @Test public void testGetLp(){
        assertEquals(0,specialEvent.getLp());
    }
    @Test
   public void testGetCash(){
        assertEquals(0,specialEvent.getCash());
    }
    @Test
   public void testGetOptionA(){
        assertEquals("test",specialEvent.getOptionA());
    }
    @Test
   public void testGetOptionB(){
        assertEquals("test",specialEvent.getOptionB());
    }
    @Test
   public void testGetMessage(){
        assertEquals("test",specialEvent.getMessage());
    }
}

