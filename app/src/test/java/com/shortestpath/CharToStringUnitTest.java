package com.shortestpath;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CharToStringUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        char ch = '7';

        String string1 = Character.toString(ch);

        String string2 = String.valueOf(ch);

        System.out.println("character is : " + ch + ". String using String.valueOf(char c):  " + string2);

        System.out.println("character is : " + ch + ". String using Character.toString(char c):  " + string1);

    }
}