package nure.ua.safoshyn.service;

import lombok.AllArgsConstructor;
import nure.ua.safoshyn.entity.Certificate;
import nure.ua.safoshyn.entity.CustomUser;
import nure.ua.safoshyn.exception.EntityNotFoundException;
import nure.ua.safoshyn.repository.CertificateRepository;
import nure.ua.safoshyn.repository.CustomUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    CertificateRepository certificateRepository;
    CustomUserRepository customUserRepository;
    @Override
    public Certificate getCertificate(String id) {
        Optional<Certificate> certificate = certificateRepository.findById(id);
        return unwrapCertificate(certificate, id);
    }

    @Override
    public Certificate saveCertificate(Certificate certificate) {
        certificate.setIsCompleted(false);
        return certificateRepository.save(certificate);
    }

    @Override
    public Certificate addCertificateToUser(String id, Long userId, Long instructorId) {

        Optional<Certificate> cert = certificateRepository.findById(id);
        Optional<CustomUser> user = customUserRepository.findById(userId);
        Optional<CustomUser> instructor = customUserRepository.findById(instructorId);
        CustomUser unwrappedUser = CustomUserServiceImpl.unwrapCustomUser(user, userId);
        CustomUser unwrappedInstructor = CustomUserServiceImpl.unwrapCustomUser(instructor, instructorId);
        Certificate certificate = unwrapCertificate(cert, id);
        certificate.setCustomUser(unwrappedUser);
        certificate.setInstructor(unwrappedInstructor);
        return certificateRepository.save(certificate);
    }

    @Override
    public void deleteCertificate(String id) {
        certificateRepository.deleteById(id);
    }

    @Override
    public List<Certificate> getCertificatesByUser(Long userId) {
        return certificateRepository.findAllByCustomUserId(userId);
    }

    @Override
    public List<Certificate> getCertificatesByInstructor(Long instructorId) {
        return certificateRepository.findAllByInstructorId(instructorId);
    }

    @Override
    public List<Certificate> getCertificates() {
        return (List<Certificate>)certificateRepository.findAll();
    }


    public static Certificate unwrapCertificate(Optional<Certificate> entity, String id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Certificate.class);
    }
}
