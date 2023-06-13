package aau.se2.glock.alpha.gameoflife.networking.packages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CheatingMessageTest {

    private CheatingMessage cheatingMessageUnderTest;
    private String payload;

    @Before
    public void setUp() {
        payload = "cheating payload";
        cheatingMessageUnderTest = new CheatingMessage(payload);
    }

    @Test
    public void testConstructor() {
        assertEquals(payload, cheatingMessageUnderTest.getPayload());
        cheatingMessageUnderTest = new CheatingMessage();
        assertTrue(cheatingMessageUnderTest instanceof CheatingMessage);
    }

    @Test
    public void testAccept() {
        TcpMessageVisitor mockVisitor = mock(TcpMessageVisitor.class);

        cheatingMessageUnderTest.accept(mockVisitor);

        verify(mockVisitor, Mockito.times(1)).visit(cheatingMessageUnderTest);
    }

    @Test
    public void testGetPayload() {
        assertEquals(payload, cheatingMessageUnderTest.getPayload());
    }

    @Test
    public void testToString() {
        String expected = "CheatingMessage{" +
                "payload='" + payload + '\'' +
                ", command='CHEAT'" +
                '}';
        assertTrue(cheatingMessageUnderTest.toString().contains(payload));
        assertTrue(cheatingMessageUnderTest.toString().contains("CHEAT"));
        assertEquals(expected, cheatingMessageUnderTest.toString());
    }
}
