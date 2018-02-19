package org.jpos.iso;

import org.junit.Test;

public class LiteralInterpreter2Test {
    @Test
    public void uninterpretPositiveLength() {
        String result = LiteralInterpreter.INSTANCE.uninterpret(
                new byte[10],
                0,
                5);
    }

    @Test
    public void uninterpretMaxLength() {
        String result = LiteralInterpreter.INSTANCE.uninterpret(
                new byte[10],
                0,
                10);
    }

    @Test
    public void uninterpretPositiveOffSet() {
        String result = LiteralInterpreter.INSTANCE.uninterpret(
                new byte[10],
                5,
                0);
    }

    @Test
    public void uninterpretMaxOffSet() {
        String result = LiteralInterpreter.INSTANCE.uninterpret(
                new byte[10],
                10,
                0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretNegativeOffSet() {
        String result = LiteralInterpreter.INSTANCE.uninterpret(
                new byte[10],
                -1,
                0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretOutOfBoundOffSet() {
        String result = LiteralInterpreter.INSTANCE.uninterpret(
                new byte[10],
                11,
                0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretNegativeLength() {
        String result = LiteralInterpreter.INSTANCE.uninterpret(
                new byte[10],
                0,
                -1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretOutOfBoundLength() {
        String result = LiteralInterpreter.INSTANCE.uninterpret(
                new byte[10],
                0,
                11);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretNegativeOffsetAndLength() {
        String result = LiteralInterpreter.INSTANCE.uninterpret(
                new byte[10],
                -1,
                -1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretOutOfBoundOffsetAndLength() {
        String result = LiteralInterpreter.INSTANCE.uninterpret(
                new byte[10],
                5,
                6);
    }

    @Test
    public void interpretPositiveOffSet() {
        new LiteralInterpreter().interpret(
                "",
                new byte[10],
                1);
    }

    @Test
    public void interpretMaxOffSet() {
        new LiteralInterpreter().interpret(
                "",
                new byte[2],
                2);
    }

    @Test
    public void interpretNonEmptyString() {
        new LiteralInterpreter().interpret(
                "AAA",
                new byte[5],
                0);
    }

    @Test
    public void interpretMaxLength() {
        new LiteralInterpreter().interpret(
                "12345",
                new byte[5],
                0);
    }


    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void interpretNegativeOffSet() {
        new LiteralInterpreter().interpret(
                "",
                new byte[10],
                -1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void interpretOutOfBoundOffSet() {
        new LiteralInterpreter().interpret(
                "",
                new byte[2],
                3);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void interpretOutOfBoundLength() {
        new LiteralInterpreter().interpret(
                "123456",
                new byte[5],
                0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void interpretOutOfBoundOffsetAndLength() {
        new LiteralInterpreter().interpret(
                "123456",
                new byte[10],
                5);
    }
}
