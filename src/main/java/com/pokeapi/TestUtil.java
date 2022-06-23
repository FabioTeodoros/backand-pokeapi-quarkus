package com.pokeapi;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.logging.Logger;

import static org.mockito.Mockito.mock;

public class TestUtil {
    private static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

    public static Logger getLoggerMock(Class testClass) throws Exception {
        Logger loggerMock = mock(Logger.class);
        setFinalStatic(testClass.getDeclaredField("LOGGER"), loggerMock);
        return loggerMock;
    }
}
