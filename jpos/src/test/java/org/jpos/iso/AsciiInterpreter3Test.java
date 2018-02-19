/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2018 jPOS Software SRL
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jpos.iso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class AsciiInterpreter3Test {

    @Test
    public void uninterpretPositiveLength() {
        String result = AsciiInterpreter.INSTANCE.uninterpret(
                new byte[10],
                0,
                5);
    }

    @Test
    public void uninterpretMaxLength() {
        String result = AsciiInterpreter.INSTANCE.uninterpret(
                new byte[10],
                0,
                10);
    }

    @Test
    public void uninterpretPositiveOffSet() {
        String result = AsciiInterpreter.INSTANCE.uninterpret(
                new byte[10],
                5,
                0);
    }

    @Test
    public void uninterpretMaxOffSet() {
        String result = AsciiInterpreter.INSTANCE.uninterpret(
                new byte[10],
                10,
                0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretNegativeOffSet() {
        String result = AsciiInterpreter.INSTANCE.uninterpret(
                new byte[10],
                -1,
                0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretOutOfBoundOffSet() {
        String result = AsciiInterpreter.INSTANCE.uninterpret(
                new byte[10],
                11,
                0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretNegativeLength() {
        String result = AsciiInterpreter.INSTANCE.uninterpret(
                new byte[10],
                0,
                -1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretOutOfBoundLength() {
        String result = AsciiInterpreter.INSTANCE.uninterpret(
                new byte[10],
                0,
                11);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretNegativeOffsetAndLength() {
        String result = AsciiInterpreter.INSTANCE.uninterpret(
                new byte[10],
                -1,
                -1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void uninterpretOutOfBoundOffsetAndLength() {
        String result = AsciiInterpreter.INSTANCE.uninterpret(
                new byte[10],
                5,
                6);
    }

    @Test
    public void interpretPositiveOffSet() {
        new AsciiInterpreter().interpret(
                "",
                new byte[10],
                1);
    }

    @Test
    public void interpretMaxOffSet() {
        new AsciiInterpreter().interpret(
                "",
                new byte[2],
                2);
    }

    @Test
    public void interpretNonEmptyString() {
        new AsciiInterpreter().interpret(
                "AAA",
                new byte[5],
                0);
    }

    @Test
    public void interpretMaxLength() {
        new AsciiInterpreter().interpret(
                "12345",
                new byte[5],
                0);
    }


    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void interpretNegativeOffSet() {
        new AsciiInterpreter().interpret(
                "",
                new byte[10],
                -1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void interpretOutOfBoundOffSet() {
        new AsciiInterpreter().interpret(
                "",
                new byte[2],
                3);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void interpretOutOfBoundLength() {
        new AsciiInterpreter().interpret(
                "123456",
                new byte[5],
                0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void interpretOutOfBoundOffsetAndLength() {
        new AsciiInterpreter().interpret(
                "123456",
                new byte[10],
                5);
    }
}
