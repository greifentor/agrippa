package de.ollie.agrippa.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import de.ollie.agrippa.core.model.Page;
import de.ollie.agrippa.core.model.PageParameters;
import de.ollie.agrippa.core.model.User;
import de.ollie.agrippa.core.service.exception.PersistenceFailureException;
import de.ollie.agrippa.core.service.exception.PersistenceFailureException.Reason;
import de.ollie.agrippa.core.service.exception.PersistenceFailureException.ValidationFailure;
import de.ollie.agrippa.core.service.port.persistence.UserPersistencePort;
import de.ollie.agrippa.persistence.converter.PageConverter;
import de.ollie.agrippa.persistence.converter.PageParametersToPageableConverter;
import de.ollie.agrippa.persistence.converter.UserDBOConverter;
import de.ollie.agrippa.persistence.entity.UserDBO;
import de.ollie.agrippa.persistence.repository.UserDBORepository;
import lombok.Generated;

/**
 * A generated JPA persistence adapter for users.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class UserGeneratedJPAPersistenceAdapter implements UserPersistencePort {

	@Inject
	protected UserDBOConverter converter;
	@Inject
	protected UserDBORepository repository;

	@Inject
	protected PageParametersToPageableConverter pageParametersToPageableConverter;

	protected PageConverter<User, UserDBO> pageConverter;

	@PostConstruct
	public void postConstruct() {
		pageConverter = new PageConverter<>(converter);
	}

	@Override
	public User create(User model) {
		model.setId(-1);
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public List<User> findAll() {
		return converter.toModel(repository.findAll());
	}

	@Override
	public Page<User> findAll(PageParameters pageParameters) {
		return pageConverter.convert(repository.findAll(pageParametersToPageableConverter.convert(pageParameters)));
	}

	@Override
	public Optional<User> findById(Long id) {
		return repository.findById(id).map(dbo -> converter.toModel(dbo));
	}

	@Override
	public User update(User model) {
		ensureNoViolationsFound(model);
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	private void ensureNoViolationsFound(User model) {
		List<ValidationFailure> failures = new ArrayList<>();
		if (model.getName() == null) {
			failures.add(new ValidationFailure(Reason.NOT_NULL, "User", "name"));
		}
		if (model.getToken() == null) {
			failures.add(new ValidationFailure(Reason.NOT_NULL, "User", "token"));
		}
		if (!findByGlobalId(model.getGlobalId())
				.filter(user -> user.getId() != model.getId())
				.isEmpty()) {
			failures.add(new ValidationFailure(Reason.UNIQUE, "User", "globalId"));
		}
		if (!failures.isEmpty()) {
			throw new PersistenceFailureException("" + model.getId(), failures);
		}
	}

	@Override
	public void delete(User model) {
		repository.delete(converter.toDBO(model));
	}

	@Override
	public Optional<User> findByGlobalId(String globalId) {
		return Optional.ofNullable(converter.toModel(repository.findByGlobalId(globalId).orElse(null)));
	}

}