package nure.ua.safoshyn.service;

import nure.ua.safoshyn.entity.Certificate;

import java.util.List;

public interface CertificateService {
    Certificate getCertificate(String id);
    Certificate saveCertificate(Certificate certificate);
    Certificate addCertificateToUser(Certificate certificate, Long userId, Long instructorId );
    void deleteCertificate(String id);
    List<Certificate> getCertificatesByUser(Long userId);
    List<Certificate> getCertificatesByInstructor(Long instructorId);
    List<Certificate> getCertificates();
}
