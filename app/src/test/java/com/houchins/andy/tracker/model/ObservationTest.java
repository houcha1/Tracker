package com.houchins.andy.tracker.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

/**
 * Verifies the {@link Observation} class
 */
@RunWith(JUnit4.class)
public class ObservationTest {

    @Test
    public void testCervixFirmness() {
        Observation observation = new Observation();
        for (CervixFirmness value : CervixFirmness.values()) {
            observation.reset();
            observation.setCervixFirmness(value);
            assertEquals(value, observation.getCervixFirmness());
        }
    }

    @Test
    public void testCervixHeight() {
        Observation observation = new Observation();
        for (CervixHeight value : CervixHeight.values()) {
            observation.reset();
            observation.setCervixHeight(value);
            assertEquals(value, observation.getCervixHeight());
        }
    }

    @Test
    public void testCervixOpenness() {
        Observation observation = new Observation();
        for (CervixOpenness value : CervixOpenness.values()) {
            observation.reset();
            observation.setCervixOpenness(value);
            assertEquals(value, observation.getCervixOpenness());
        }
    }

    @Test
    public void testMucus() {
        Observation observation = new Observation();
        for (Mucus value : Mucus.values()) {
            observation.reset();
            observation.setMucus(value);
            assertEquals(value, observation.getMucus());
        }
    }

    @Test
    public void testFlow() {
        Observation observation = new Observation();
        for (Flow value : Flow.values()) {
            observation.reset();
            observation.setFlow(value);
            assertEquals(value, observation.getFlow());
        }
    }


}