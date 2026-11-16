package com.assessment.helloworldapi.service.impl;

import com.assessment.helloworldapi.exception.InvalidNameException;
import com.assessment.helloworldapi.service.GreetingService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GreetingServiceImplTest {

    private final GreetingService greetingService =
            new GreetingServiceImpl();

    @Test
    void lowercaseNameReturnsGreeting() {
        String result = greetingService.greet("alice");

        assertEquals("Hello Alice", result);
    }

    @Test
    void uppercaseInitialReturnsGreeting() {
        String result = greetingService.greet("Alice");

        assertEquals("Hello Alice", result);
    }

    @Test
    void boundaryLetterMReturnsGreeting() {
        assertEquals("Hello M", greetingService.greet("M"));
        assertEquals("Hello M", greetingService.greet("m"));
    }

    @Test
    void boundaryLetterNIsRejected() {
        assertThrows(
                InvalidNameException.class,
                () -> greetingService.greet("N")
        );

        assertThrows(
                InvalidNameException.class,
                () -> greetingService.greet("n")
        );
    }

    @Test
    void nameStartingWithZIsRejected() {
        assertThrows(
                InvalidNameException.class,
                () -> greetingService.greet("Zara")
        );
    }

    @Test
    void nullNameIsRejected() {
        assertThrows(
                InvalidNameException.class,
                () -> greetingService.greet(null)
        );
    }

    @Test
    void emptyNameIsRejected() {
        assertThrows(
                InvalidNameException.class,
                () -> greetingService.greet("")
        );
    }

    @Test
    void blankNameIsRejected() {
        assertThrows(
                InvalidNameException.class,
                () -> greetingService.greet("   ")
        );
    }

    @Test
    void numericInitialIsRejected() {
        assertThrows(
                InvalidNameException.class,
                () -> greetingService.greet("123")
        );
    }

    @Test
    void nonEnglishInitialIsRejected() {
        assertThrows(
                InvalidNameException.class,
                () -> greetingService.greet("é Alice")
        );
    }

    @Test
    void surroundingWhitespaceIsRemoved() {
        String result = greetingService.greet("  alice  ");

        assertEquals("Hello Alice", result);
    }

    @Test
    void charactersAfterInitialArePreserved() {
        String result = greetingService.greet("ALICE");

        assertEquals("Hello ALICE", result);
    }
}
