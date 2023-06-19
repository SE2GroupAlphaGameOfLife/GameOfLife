package aau.se2.glock.alpha.gameoflife.networking.packages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReportPlayerMessageTest {

    @Mock
    TcpMessageVisitor mockVisitor;

    private ReportPlayerMessage reportPlayerMessage;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReportPlayerMessage() {
        String payload = "Test Payload";
        reportPlayerMessage = new ReportPlayerMessage(payload);
        assertNotNull(reportPlayerMessage);
        assertEquals(payload, reportPlayerMessage.getPayload());
        reportPlayerMessage = new ReportPlayerMessage();
        assertTrue(reportPlayerMessage instanceof ReportPlayerMessage);
    }

    @Test
    public void testAccept() {
        String payload = "Test Payload";
        reportPlayerMessage = new ReportPlayerMessage(payload);
        reportPlayerMessage.accept(mockVisitor);
        verify(mockVisitor).visit(reportPlayerMessage);
    }

    @Test
    public void testToString() {
        String payload = "Test Payload";
        reportPlayerMessage = new ReportPlayerMessage(payload);
        String expectedString = "ReportPlayerMessage{payload='" + payload + "'}";
        assertEquals(expectedString, reportPlayerMessage.toString());
    }
}
