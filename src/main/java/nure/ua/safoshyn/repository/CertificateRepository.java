package nure.ua.safoshyn.repository;

import nure.ua.safoshyn.entity.Certificate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends CrudRepository<Certificate, Long> {

    Optional<Certificate> findById(String id);
    void deleteById(String id);
    List<Certificate> findAllByCustomUserId(Long id);
    List<Certificate> findAllByInstructorId(Long id);
}
