package de.ollie.agrippa.gui;

import static de.ollie.agrippa.util.Check.ensure;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

import org.springframework.stereotype.Component;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;

import de.ollie.agrippa.core.model.localization.LocalizationSO;
import de.ollie.agrippa.core.service.JWTService.AuthorizationData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.ToString;

/**
 * An object to hold data during the session.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Component
@Data
@Generated
@VaadinSessionScope
public class SessionData {

	@AllArgsConstructor
	@EqualsAndHashCode
	@Getter
	@ToString
	public static class ReturnUrlData {

		public ReturnUrlData(String url) {
			this(url, Map.of());
		}

		private String url;
		private Map<String, List<String>> parameters;

	}

	private static int counter = 0;

	private AccessChecker accessChecker;
	private AuthorizationData authorizationData;
	private SessionId id = new SessionId("agrippa-" + (counter++));
	private LocalizationSO localization = LocalizationSO.DE;
	private Map<String, Object> parameters = new HashMap<>();
	private Stack<ReturnUrlData> returnUrlDatas = new Stack<>();
	private LocalDateTime validUntil;

	public void addReturnUrl(String url) {
		addReturnUrl(url, Map.of());
	}

	public void addReturnUrl(String url, Map<String, List<String>> parameters) {
		ensure(url != null, "url cannot be null!");
		ensure(!url.isEmpty(), "url cannot be empty!");
		ensure(parameters != null, "parameters cannot be null!");
		System.out.println("#before: " + returnUrlDatas);
		returnUrlDatas.push(new ReturnUrlData(url, parameters));
		System.out.println("#after:  " + returnUrlDatas);
	}

	public Optional<ReturnUrlData> getReturnUrl() {
		System.out.println("#get(b): " + returnUrlDatas);
		return Optional.ofNullable(!returnUrlDatas.isEmpty() ? returnUrlDatas.pop() : null);
	}

	public String getUserName() {
		return authorizationData.getUser().getName();
	}

	public Optional<Object> findParameter(String id) {
		return Optional.ofNullable(parameters.get(id));
	}

	public void setParameter(String id, Object obj) {
		parameters.put(id, obj);
	}

}