package de.ollie.agrippa.core.service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TodoWarnLevelConfiguration {

    @Value("${warn.level1.offset.minutes:3410}") // 56 h
	private int warnLevel1OffsetMinutes;

    @Value("${warn.level2.offset.minutes:1980}") // 32 h
	private int warnLevel2OffsetMinutes;

    @Value("${warn.level3.offset.minutes:540}") // 8 h
	private int warnLevel3OffsetMinutes;

	public int getWarnLevel1OffsetInMinutes() {
		return warnLevel1OffsetMinutes;
	}

	public int getWarnLevel2OffsetInMinutes() {
		return warnLevel2OffsetMinutes;
	}

	public int getWarnLevel3OffsetInMinutes() {
		return warnLevel3OffsetMinutes;
	}

}
