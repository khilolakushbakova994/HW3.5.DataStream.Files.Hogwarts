package hogwarts.HW35.DataStream.Files.Hogwarts.repository;

import hogwarts.HW35.DataStream.Files.Hogwarts.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional <Avatar> findAvatarById (Long studentId);
}
