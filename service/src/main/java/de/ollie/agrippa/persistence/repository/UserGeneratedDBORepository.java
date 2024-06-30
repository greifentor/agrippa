package de.ollie.agrippa.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ollie.agrippa.persistence.entity.UserDBO;
import lombok.Generated;
import java.util.Optional;

/**
 * A generated JPA repository for users.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Repository
public interface UserGeneratedDBORepository extends JpaRepository<UserDBO, Long> {

	Optional<UserDBO> findByGlobalId(String globalId);

}