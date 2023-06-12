package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.core.gamecards.Card;
import aau.se2.glock.alpha.gameoflife.core.gamecards.Event;
import aau.se2.glock.alpha.gameoflife.core.gamecards.Stack;
import aau.se2.glock.alpha.gameoflife.core.logic.LogicalField;

public class LogicalFieldTest {

    @Mock
    private GameField gameField;

    @Mock
    private Files files;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Gdx.files = files;

        // Call the buildStack() method to initialize the cardList in Stack
        Stack stack = Stack.getINSTANCE();
        stack.buildStack();
    }

    @Test
    public void testGetEventWhenSpecialField() {
        LogicalField logicalField = new LogicalField(gameField);
        logicalField.setSpecial(true);

        Event event = logicalField.getEvent();

        assertNull(event);
    }

    @Test
    public void testGetEventWhenNonSpecialField() {
        LogicalField logicalField = new LogicalField(gameField);
        logicalField.setSpecial(false);

        logicalField.setType(3);
        assertEquals(logicalField.getType(), 3);

        // Mock the Card object
        Card card = mock(Card.class);
        Event expectedEvent = mock(Event.class);
        when(card.getEvent(anyInt())).thenReturn(expectedEvent);

        // Initialize cardList with some elements
        List<Card> cardList = new ArrayList<>();
        cardList.add(card);

        // Mock the Stack.getINSTANCE() method to return the initialized cardList
        Stack stack = mock(Stack.class);
        when(stack.getTopCard()).thenReturn(cardList.get(0)); // Always return the first element

        // Set the cardList in the Stack instance
        Stack.INSTANCE = stack;
        Stack.INSTANCE.addCards(cardList);

        // Call the method under test
        Event event = logicalField.getEvent();

        // Assert the result
        assertSame(expectedEvent, event);
        verify(card).getEvent(logicalField.getType());
        verify(stack).getTopCard();
    }
}