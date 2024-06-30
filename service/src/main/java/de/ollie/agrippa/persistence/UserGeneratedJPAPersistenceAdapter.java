package de.ollie.agrippa.persistence;

import static de.ollie.agrippa.util.Check.ensure;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import de.ollie.agrippa.core.model.Page;
import de.ollie.agrippa.core.model.PageParameters;
import de.ollie.agrippa.core.model.User;
import de.ollie.agrippa.core.service.exception.NotNullConstraintViolationException;
import de.ollie.agrippa.core.service.exception.UniqueConstraintViolationException;
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
		ensure(
				model.getName() != null,
				() -> new NotNullConstraintViolationException("User field name cannot be null.", "User", "name"));
		ensure(
				model.getToken() != null,
				() -> new NotNullConstraintViolationException("User field token cannot be null.", "User", "token"));
		ensure(
				findByGlobalId(model.getGlobalId())
						.filter(user -> user.getId() != model.getId())
						.isEmpty(),
				() -> new UniqueConstraintViolationException("globalId '" + model.getGlobalId() + "' is already set for another record", "User", "globalId"));
		return converter.toModel(repository.save(converter.toDBO(model)));
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