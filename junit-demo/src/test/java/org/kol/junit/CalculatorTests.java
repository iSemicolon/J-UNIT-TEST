package org.kol.junit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTests {

    Calculator calculator;



    @Test
    public void addTest(){

        calculator=new Calculator();
        assertEquals(9, calculator.add(4,5));
        assertEquals(12, calculator.add(12,0));
    }

    @Test
    public void subTest(){

        calculator=new Calculator();
        assertEquals(-1, calculator.sub(4,5));
        assertEquals(4, calculator.sub(7,3));
    }

    @Test
    public void mulTest(){

        calculator=new Calculator();
        assertEquals(20, calculator.mul(4,5));
        assertEquals(21, calculator.mul(3,7));
    }

    @Test
    public void divTest(){

        calculator=new Calculator();
        assertEquals(8, calculator.div(40,5));
        assertEquals(0, calculator.div(3,0));
    }
}
