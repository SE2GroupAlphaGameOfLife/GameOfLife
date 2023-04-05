package com.example.gameoflife;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.gameoflife.gamecards.FillData;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void test() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test2(){
        FillData testdata = new FillData();

        testdata.fillEventList();
        testdata.fillCardList();
        /*
        System.out.println(testdata.cardList.size()+" Unit test");
        System.out.println(testdata.addData.size()+" events");

        System.out.println(testdata.cardList.get(7).getEvent(0).toString()+"--");
        System.out.println(testdata.cardList.get(7).getEvent(3).toString()+"--");

        System.out.println(testdata.addData.get(32));
        for (int i = 0; i < testdata.addData.size(); i++) {
            System.out.println(testdata.addData.get(i).getText());
        }

         */

        assertEquals(250,testdata.cardList.get(7).getEvent(3).getLp());
    }
}