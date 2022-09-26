package eu.wemakesoftware.jt.navsystem.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Vector2DTest {

    @Test
    void checkLengthCalculation(){
        assertEquals(1.4142, new Vector2D(1,1).length(),0.001);
    }

    @Test
    void checkDistanceCalculation() {
        assertEquals(5, new Vector2D(3,4).getDistance(new Vector2D()));
    }

    @Test
    void checkScaling() {
        assertEquals(new Vector2D(6,8), new Vector2D(3,4).scale(2));
    }
    
}
