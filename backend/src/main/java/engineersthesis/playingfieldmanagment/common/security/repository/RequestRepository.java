package engineersthesis.playingfieldmanagment.common.security.repository;

import engineersthesis.playingfieldmanagment.common.security.model.Request;
import engineersthesis.playingfieldmanagment.common.security.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByStatus(Status status);
}
