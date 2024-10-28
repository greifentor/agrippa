package de.ollie.agrippa.gui.vaadin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.agrippa.core.model.localization.LocalizationSO;
import de.ollie.agrippa.core.service.CurrentDateTimeService;
import de.ollie.agrippa.core.service.localization.ResourceManager;

@ExtendWith(MockitoExtension.class)
class DueDateFormatterTest {

	private static final int DAY = 28;
	private static final int HOUR = 19;
	private static final int MINUTE = 11;

	private static final LocalDateTime DUE_DATE = LocalDateTime.of(2024, 10, DAY, HOUR, MINUTE, 42);

	@Mock
	private CurrentDateTimeService currentDateTimeService;

	@Mock
	private ResourceManager resourceManager;

	@InjectMocks
	private DueDateFormatter unitUnderTest;

	@Nested
	class TestsOfMethod_format_LocalDateTime {

		@Test
		void throwsAnException_passingANullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.format(null));
		}

		@Test
		void returnsTheHoursAndMinuteOnly_passingATodaysDate() {
			// Prepare
			String expected = HOUR + ":" + MINUTE;
			when(currentDateTimeService.now()).thenReturn(DUE_DATE);
			// Run & Chick
			assertEquals(expected, unitUnderTest.format(DUE_DATE));
		}

		@ParameterizedTest
		@CsvSource({ "1,Di", "2,Mi", "3,Do", "4,Fr", "5,Sa", "6,So" })
		void returnsTheHoursMinuteAndWeekdayOnly_passingADateFromTomorrowTilInOneWeek(int dayOffset, String dayToken) {
			// Prepare
			String expected = "(" + dayToken + ") " + HOUR + ":" + MINUTE;
			when(currentDateTimeService.now()).thenReturn(DUE_DATE);
			when(resourceManager.getLocalizedString("day." + (dayOffset + 1) + ".token", LocalizationSO.DE))
					.thenReturn(dayToken);
			// Run & Chick
			assertEquals(expected, unitUnderTest.format(DUE_DATE.plusDays(dayOffset)));
		}

		@Test
		void returnsTheFullDate_passingADateMoreThanSixDaysInTheFuture() {
			// Prepare
			String expected = DUE_DATE.plusDays(7).toString().replace("T", " ");
			when(currentDateTimeService.now()).thenReturn(DUE_DATE);
			// Run & Chick
			assertEquals(expected, unitUnderTest.format(DUE_DATE.plusDays(7)));
		}

	}

}
