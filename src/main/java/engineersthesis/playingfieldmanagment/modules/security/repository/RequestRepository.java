package engineersthesis.playingfieldmanagment.modules.security.repository;

import engineersthesis.playingfieldmanagment.modules.security.model.Status;
import engineersthesis.playingfieldmanagment.modules.security.model.WorkerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<WorkerRequest, Long> {
    List<WorkerRequest> findAllByStatus(Status status);

}
