package de.ollie.agrippa.core.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurrentDateServiceImplTest {

    @InjectMocks
    private CurrentDateServiceImpl unitUnderTest;

    @Nested
    class TestsOfMethod_now {

        @Test
        void returnsALocalDateTimeObject() {
            assertNotNull(unitUnderTest.now());
        }

        @Test
        void returnsAnotherLocalDateTimeObject_whenCalledTwice() {
            assertNotSame(unitUnderTest.now(), unitUnderTest.now());
        }

    }

}
