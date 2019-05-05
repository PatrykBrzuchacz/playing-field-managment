package engineersthesis.playingfieldmanagment.common.security.repository;

import engineersthesis.playingfieldmanagment.common.security.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RequestRepository extends JpaRepository<Request, Long> {

}
