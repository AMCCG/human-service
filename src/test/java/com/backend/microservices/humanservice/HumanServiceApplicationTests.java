package com.backend.microservices.humanservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

@SpringBootTest
class MainAppTests {

    @Test
    @DisplayName("Main Application run success")
    void mainSuccess() {
        try (MockedStatic mocked = mockStatic(SpringApplication.class)) {
            mocked.when(() -> SpringApplication.run(eq(MainApp.class), any())).thenReturn(null);
            assertDoesNotThrow(() -> MainApp.main(new String[]{}));
        }
    }

}
