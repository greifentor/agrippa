package de.ollie.agrippa.core.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurrentDateTimeServiceImplTest {

    @InjectMocks
    private CurrentDateTimeServiceImpl unitUnderTest;

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

        @Test
        void returnsSecondObjectIsAfterFirstOne_whenCalledTwice() throws Exception {
            LocalDateTime first = unitUnderTest.now();
            TimeUnit.MILLISECONDS.sleep(1);
            LocalDateTime second = unitUnderTest.now();
            assertTrue(second.isAfter(first));
        }

    }

}
