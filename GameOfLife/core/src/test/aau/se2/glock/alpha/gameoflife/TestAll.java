package aau.se2.glock.alpha.gameoflife;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import aau.se2.glock.alpha.gameoflife.core.BoardTest;
import aau.se2.glock.alpha.gameoflife.core.GameFieldTest;
import aau.se2.glock.alpha.gameoflife.core.NormalEventDataTest;
import aau.se2.glock.alpha.gameoflife.core.NormalEventTest;
import aau.se2.glock.alpha.gameoflife.core.PlayerTest;
import aau.se2.glock.alpha.gameoflife.core.SpecialEventDataTest;
import aau.se2.glock.alpha.gameoflife.core.SpecialEventTest;
import aau.se2.glock.alpha.gameoflife.core.gamecards.CardTest;
import aau.se2.glock.alpha.gameoflife.core.gamecards.StackTest;
import aau.se2.glock.alpha.gameoflife.core.jobs.JobDataTest;
import aau.se2.glock.alpha.gameoflife.core.jobs.JobTest;
import aau.se2.glock.alpha.gameoflife.core.logic.LogicalFieldTest;
import aau.se2.glock.alpha.gameoflife.core.logic.PlayerCheatedTest;
import aau.se2.glock.alpha.gameoflife.core.special.BuildingTest;
import aau.se2.glock.alpha.gameoflife.core.special.CarTest;
import aau.se2.glock.alpha.gameoflife.core.special.SpecialDataTest;
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
        SpecialDataTest.class,
        JsonFileReaderTest.class,
        //BoardTest.class,          //No tests
        //GameFieldTest.class,      //No tests
        NormalEventDataTest.class,
        NormalEventTest.class,
        PlayerTest.class,
        SpecialEventTest.class,
        SpecialEventDataTest.class,
        NetworkTest.class,
        GameOfLifeTest.class,
})
public class TestAll {
}