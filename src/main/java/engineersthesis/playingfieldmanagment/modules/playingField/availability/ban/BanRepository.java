package engineersthesis.playingfieldmanagment.modules.playingField.availability.ban;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BanRepository extends JpaRepository<Ban, Long> {
    boolean existsByPlayingField_IdAndUser_Id(Long playingFieldId, Long userId);

    void deleteByPlayingField_IdAndUser_Id(Long playingFieldId, Long userId);

    List<Ban> findAllByPlayingField_Id(Long playingFieldId);
}
