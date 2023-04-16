package com.mygdx.gameoflife.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.mygdx.gameoflife.core.AvailableServerDetails;

import org.junit.Test;

public class AvailableServerDetailsTest {
    @Test
    public void testHost() {
        AvailableServerDetails details = new AvailableServerDetails("example.com", "192.168.0.1");
        assertEquals("example.com", details.getHost());

        details.setHost("newhost.com");
        assertEquals("newhost.com", details.getHost());
    }

    @Test
    public void testIp() {
        AvailableServerDetails details = new AvailableServerDetails("example.com", "192.168.0.1");
        assertEquals("192.168.0.1", details.getIp());

        details.setIp("192.168.0.2");
        assertEquals("192.168.0.2", details.getIp());
    }

    @Test
    public void testConstructor() {
        AvailableServerDetails details = new AvailableServerDetails("example.com", "192.168.0.1");
        assertNotNull(details);
        assertEquals("example.com", details.getHost());
        assertEquals("192.168.0.1", details.getIp());
    }
}
