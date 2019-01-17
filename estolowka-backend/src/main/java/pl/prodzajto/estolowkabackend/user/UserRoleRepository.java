package pl.prodzajto.estolowkabackend.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
    UserRole findByRole(String role);
}
