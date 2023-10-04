package MesuresImperiales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MesuresImperialesTest {
    public MesuresImperiales m0 ;
    public MesuresImperiales m1 ;
    public MesuresImperiales m12 ;

    public MesuresImperiales m2716 ;
    public MesuresImperiales m8964 ;
    public MesuresImperiales m56364 ;

    @BeforeEach
    public void SetUp() {
        m0 = new MesuresImperiales(0) ;
        m1 = new MesuresImperiales(1) ;
        m12 = new MesuresImperiales(1, 2) ;
        m2716 = new MesuresImperiales(2, 7, 16) ;
        m8964 = new MesuresImperiales(8, 9, 64) ;
        m56364 = new MesuresImperiales(5, 63, 64) ;
    }

    @Test
    void negative_measures() {
        MesuresImperiales test = new MesuresImperiales(-2, 1, 4) ;
        assertEquals("-1 -3/4", test.to_string()) ;

        MesuresImperiales test1 = new MesuresImperiales(2, -15, 4) ;
        assertEquals("-1 -3/4", test1.to_string()) ;

        MesuresImperiales test2 = new MesuresImperiales(2, 15, -4) ;
        assertEquals("-1 -3/4", test2.to_string()) ;
    }

    @Test
    void float_to_fraction() {
        MesuresImperiales m = new MesuresImperiales(0.5, 128) ;
        assertEquals("1/2", m.to_string()) ;

        MesuresImperiales m12116 = new MesuresImperiales(12.0625, 128) ;
        assertEquals("12 1/16", m12116.to_string()) ;

        double x = 1.0 / 3.0 ;

        MesuresImperiales m13 = new MesuresImperiales(x, 128) ;
        assertEquals("43/128", m13.to_string()) ;

        MesuresImperiales m1364 = new MesuresImperiales(x, 64) ;
        assertEquals("21/64", m1364.to_string()) ;
    }

    @Test
    void to_string() {
        MesuresImperiales m = new MesuresImperiales(0, 4, 2) ;
        assertEquals("2", m.to_string()) ;

        MesuresImperiales m2 = new MesuresImperiales(1, 7, 3) ;
        assertEquals("3 1/3", m2.to_string()) ;

        MesuresImperiales m3 = new MesuresImperiales(4, 12, 6) ;
        assertEquals("6", m3.to_string()) ;
    }

    @Test
    void to_double() {
        assertEquals(0, m0.to_double()) ;
        assertEquals(1.0, m1.to_double()) ;
        assertEquals(0.5, m12.to_double()) ;
        assertEquals(2.4375, m2716.to_double());
        assertEquals(8.140625, m8964.to_double());
        assertEquals(5.984375, m56364.to_double()) ;
        assertEquals(-2.4375, new MesuresImperiales(-2, -7, 16).to_double()) ;
        assertEquals(-2.4375, new MesuresImperiales(-2, 7, -16).to_double()) ;
    }

    @Test
    void egal() {
        assertTrue(m1.egal(m1)) ;
        assertTrue(m0.egal(m0)) ;
        assertTrue(m56364.egal(m56364)) ;
        assertFalse(m1.egal(m0)) ;
        assertFalse(m0.egal(m1)) ;
        assertFalse(m56364.egal(m2716)) ;
    }

    @Test
    void add() {
        MesuresImperiales m1 = new MesuresImperiales(1, 3, 4) ;
        MesuresImperiales m2 = new MesuresImperiales(2, 7, 16) ;
        assertEquals("4 3/16", m1.add(m2).to_string());

        MesuresImperiales m3 = new MesuresImperiales(0, 1, 2) ;
        MesuresImperiales m4 = new MesuresImperiales(0, 1, 2) ;
        assertEquals("1", m3.add(m4).to_string()) ;

        MesuresImperiales m0 = new MesuresImperiales(0, 0, 1) ;
        assertEquals("1 3/4", m0.add(m1).to_string()) ;
    }

    @Test
    void sub() {
        MesuresImperiales m1 = new MesuresImperiales(1, 7, 3) ;
        assertEquals("0", m1.sub(m1).to_string()) ;

        MesuresImperiales m2 = new MesuresImperiales(3, 3,4) ;
        MesuresImperiales m3 = new MesuresImperiales(1, 7, 16) ;
        assertEquals("2 5/16", m2.sub(m3).to_string()) ;
    }

    @Test
    void multiplierParUnReel() {
        assertEquals("1 7/32", m2716.multiplierParUnReel(0.5, 128).to_string()) ;
        assertEquals("1/8", m12.multiplierParUnReel(0.25, 128).to_string()) ;
    }

    @Test
    public void value_of() {
        MesuresImperiales test = MesuresImperiales.value_of("4 5/16") ;
        MesuresImperiales attendu = new MesuresImperiales(4, 5, 16) ;
        assertTrue(test.egal(attendu)) ;

        MesuresImperiales test2 = MesuresImperiales.value_of("138/32") ;
        assertTrue(test2.egal(attendu)) ;

        MesuresImperiales test3 = MesuresImperiales.value_of("4") ;
        MesuresImperiales attendu3 = new MesuresImperiales(4) ;
        assertTrue(test3.egal(attendu3)) ;
    }
}