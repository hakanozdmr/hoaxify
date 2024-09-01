package hakan.ozdmr.hoaxifyws.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    User findByActivationToken(String token);
}
