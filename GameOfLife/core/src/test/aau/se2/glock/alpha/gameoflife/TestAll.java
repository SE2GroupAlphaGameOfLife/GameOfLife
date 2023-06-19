package aau.se2.glock.alpha.gameoflife;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import aau.se2.glock.alpha.gameoflife.core.PlayerTest;
import aau.se2.glock.alpha.gameoflife.core.gamecards.CardTest;
import aau.se2.glock.alpha.gameoflife.core.gamecards.EventDataTest;
import aau.se2.glock.alpha.gameoflife.core.gamecards.NormalEventTest;
import aau.se2.glock.alpha.gameoflife.core.jobs.JobDataTest;
import aau.se2.glock.alpha.gameoflife.core.jobs.JobTest;
import aau.se2.glock.alpha.gameoflife.core.logic.LogicalFieldTest;
import aau.se2.glock.alpha.gameoflife.core.logic.PlayerCheatedTest;
import aau.se2.glock.alpha.gameoflife.core.logic.SpecialEventDataTest;
import aau.se2.glock.alpha.gameoflife.core.logic.SpecialEventTest;
import aau.se2.glock.alpha.gameoflife.core.special.BuildingTest;
import aau.se2.glock.alpha.gameoflife.core.special.CarTest;
import aau.se2.glock.alpha.gameoflife.core.utilities.JsonFileReaderTest;
import aau.se2.glock.alpha.gameoflife.networking.NetworkTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        //StackTest.class,          //No tests
        CardTest.class,
        JobDataTest.class,
        JobTest.class,
        LogicalFieldTest.class,
        PlayerCheatedTest.class,
        SpecialEventTest.class,
        BuildingTest.class,
        CarTest.class,
        aau.se2.glock.alpha.gameoflife.core.special.SpecialDataTest.class,
        JsonFileReaderTest.class,
        //BoardTest.class,          //No tests
        //GameFieldTest.class,      //No tests
        EventDataTest.class,
        NormalEventTest.class,
        PlayerTest.class,
        SpecialEventTest.class,
        SpecialEventDataTest.class,
        NetworkTest.class,
        GameOfLifeTest.class,
})
public class TestAll {
}