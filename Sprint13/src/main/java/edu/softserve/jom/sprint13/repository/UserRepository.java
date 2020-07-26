package edu.softserve.jom.sprint13.repository;

import edu.softserve.jom.sprint13.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAllByRole(User.Role role);

}
