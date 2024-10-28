package de.ollie.agrippa.gui.vaadin;

import static de.ollie.agrippa.util.Check.ensure;

import java.time.LocalDateTime;

import javax.inject.Named;

import de.ollie.agrippa.core.model.localization.LocalizationSO;
import de.ollie.agrippa.core.service.CurrentDateTimeService;
import de.ollie.agrippa.core.service.localization.ResourceManager;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class DueDateFormatter {

	private final CurrentDateTimeService currentDateTimeService;
	private final ResourceManager resourceManager;

	public String format(LocalDateTime dueDate) {
		ensure(dueDate != null, "due date cannot be null.");
		if (currentDateTimeService.now().toLocalDate().equals(dueDate.toLocalDate())) {
			return dueDate.toString().substring(11, 16);
		} else if (currentDateTimeService.now().plusDays(7).minusMinutes(1).isBefore(dueDate)) {
			return dueDate.toString().replace("T", " ");
		}
		String dayToken =
				resourceManager
						.getLocalizedString("day." + dueDate.getDayOfWeek().getValue() + ".token", LocalizationSO.DE);
		return "(" + dayToken + ") " + dueDate.toString().substring(11, 16);
	}

}
