package de.ollie.agrippa.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ollie.agrippa.persistence.entity.ProjectDBO;
import lombok.Generated;
import java.util.Optional;

/**
 * A generated JPA repository for projects.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Repository
public interface ProjectGeneratedDBORepository extends JpaRepository<ProjectDBO, Long> {

	Optional<ProjectDBO> findByTitle(String title);

}