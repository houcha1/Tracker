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
    public void testCervixTexture() {
        Observation observation = new Observation();
        for (CervixTexture value : CervixTexture.values()) {
            observation.reset();
            observation.setCervixTexture(value);
            assertEquals(value, observation.getCervixTexture());
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
    public void testCervixShape() {
        Observation observation = new Observation();
        for (CervixShape value : CervixShape.values()) {
            observation.reset();
            observation.setCervixShape(value);
            assertEquals(value, observation.getCervixShape());
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