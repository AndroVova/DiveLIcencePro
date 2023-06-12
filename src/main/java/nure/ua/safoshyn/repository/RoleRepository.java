package nure.ua.safoshyn.repository;

import nure.ua.safoshyn.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
