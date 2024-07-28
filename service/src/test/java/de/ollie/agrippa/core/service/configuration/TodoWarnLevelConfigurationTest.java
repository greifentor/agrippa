package de.ollie.agrippa.core.service.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class TodoWarnLevelConfigurationTest {

	static final int WARN_LEVEL1_MINUTES = 815;
	static final int WARN_LEVEL2_MINUTES = 42;
	static final int WARN_LEVEL3_MINUTES = 7;

	@InjectMocks
	private TodoWarnLevelConfiguration unitUnderTest;

	@Test
	void returnsTheCorrectMillisValuesForWarnLevel1() {
		ReflectionTestUtils.setField(unitUnderTest, "warnLevel1OffsetMinutes", WARN_LEVEL1_MINUTES);
		assertEquals(WARN_LEVEL1_MINUTES, unitUnderTest.getWarnLevel1OffsetInMinutes());
	}

	@Test
	void returnsTheCorrectMillisValuesForWarnLevel2() {
		ReflectionTestUtils.setField(unitUnderTest, "warnLevel2OffsetMinutes", WARN_LEVEL2_MINUTES);
		assertEquals(WARN_LEVEL2_MINUTES, unitUnderTest.getWarnLevel2OffsetInMinutes());
	}

	@Test
	void returnsTheCorrectMillisValuesForWarnLevel3() {
		ReflectionTestUtils.setField(unitUnderTest, "warnLevel3OffsetMinutes", WARN_LEVEL3_MINUTES);
		assertEquals(WARN_LEVEL3_MINUTES, unitUnderTest.getWarnLevel3OffsetInMinutes());
	}

}
