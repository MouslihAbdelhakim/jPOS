package org.jpos.iso;

import org.junit.Test;

public class SignedEbcdicNumberInterpreter3Test {

    @Test
    public void uninterpretPositiveLength() {
        String result = SignedEbcdicNumberInterpreter.INSTANCE.uninterpret(
                new byte[10],
                0,
                5);
    }

    @Test
    public void uninterpretMaxLength() {
        String result = SignedEbcdicNumberInterpreter.INSTANCE.uninterpret(
                new byte[10],
                0,
                10);
    }

    @Test
    public void uninterpretPositiveOffSet() {
        String result = SignedEbcdicNumberInterpreter.INSTANCE.uninterpret(
                new byte[10],
                5,
                0);
    }

    @Test
    public void uninterpretMaxOffSet() {
        String result = SignedEbcdicNumberInterpreter.INSTANCE.uninterpret(
                new byte[10],
                10,
                0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretNegativeOffSet() {
        String result = SignedEbcdicNumberInterpreter.INSTANCE.uninterpret(
                new byte[10],
                -1,
                0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretOutOfBoundOffSet() {
        String result = SignedEbcdicNumberInterpreter.INSTANCE.uninterpret(
                new byte[10],
                11,
                0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretNegativeLength() {
        String result = SignedEbcdicNumberInterpreter.INSTANCE.uninterpret(
                new byte[10],
                0,
                -1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretOutOfBoundLength() {
        String result = SignedEbcdicNumberInterpreter.INSTANCE.uninterpret(
                new byte[10],
                0,
                11);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretNegativeOffsetAndLength() {
        String result = SignedEbcdicNumberInterpreter.INSTANCE.uninterpret(
                new byte[10],
                -1,
                -1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretOutOfBoundOffsetAndLength() {
        String result = SignedEbcdicNumberInterpreter.INSTANCE.uninterpret(
                new byte[10],
                5,
                6);
    }

    @Test
    public void interpretPositiveOffSet() {
        new SignedEbcdicNumberInterpreter().interpret(
                "",
                new byte[10],
                1);
    }

    @Test
    public void interpretMaxOffSet() {
        new SignedEbcdicNumberInterpreter().interpret(
                "",
                new byte[2],
                2);
    }

    @Test
    public void interpretNonEmptyString() {
        new SignedEbcdicNumberInterpreter().interpret(
                "AAA",
                new byte[5],
                0);
    }

    @Test
    public void interpretMaxLength() {
        new SignedEbcdicNumberInterpreter().interpret(
                "12345",
                new byte[5],
                0);
    }


    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void interpretNegativeOffSet() {
        new SignedEbcdicNumberInterpreter().interpret(
                "",
                new byte[10],
                -1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void interpretOutOfBoundOffSet() {
        new SignedEbcdicNumberInterpreter().interpret(
                "",
                new byte[2],
                3);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void interpretOutOfBoundLength() {
        new SignedEbcdicNumberInterpreter().interpret(
                "123456",
                new byte[5],
                0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void interpretOutOfBoundOffsetAndLength() {
        new SignedEbcdicNumberInterpreter().interpret(
                "123456",
                new byte[10],
                5);
    }
}
