package nure.ua.safoshyn.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import nure.ua.safoshyn.SQLQuery;
import nure.ua.safoshyn.entity.Certificate;
import nure.ua.safoshyn.entity.CustomUser;
import nure.ua.safoshyn.entity.DiveClub;
import nure.ua.safoshyn.exception.EntityNotFoundException;
import nure.ua.safoshyn.repository.CustomUserRepository;
import nure.ua.safoshyn.repository.DiveClubRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DiveClubServiceImpl implements DiveClubService {
    DiveClubRepository diveClubRepository;
    CustomUserRepository customUserRepository;
    EntityManager entityManager;

    public DiveClub getDiveClub(Long id){
        Optional<DiveClub> diveClub = diveClubRepository.findById(id);
        return unwrapDiveClub(diveClub, id);
    }

    @Override
    public DiveClub saveDiveClub(DiveClub diveClub) {
        return diveClubRepository.save(diveClub);
    }

    @Override
    public void deleteDiveClub(Long id) {
        diveClubRepository.deleteById(id);
    }

    @Override
    public List<DiveClub> getDiveClubs() {
        return (List<DiveClub>)diveClubRepository.findAll();
    }

    @Override
    public List<CustomUser> getUsersInDiveClub(Long id) {
        return customUserRepository.findAllByDiveClubId(id);
    }

    @Override
    public List<Certificate> getCertificatesGivenByDiveClub(Long id) {
        String sql = String.format(SQLQuery.GET_CERTIFICATES_BY_DIVE_CLUB_ID, id);
        TypedQuery<Certificate> query = entityManager.createQuery(sql, Certificate.class);
        List<Certificate> certificatesInClub = query.getResultList();
        if (certificatesInClub.size() == 0)
            throw new EntityNotFoundException(id, DiveClub.class ,Certificate.class); //todo: new exception
        return certificatesInClub;
    }


    public static DiveClub unwrapDiveClub(Optional<DiveClub> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, DiveClub.class);
    }
}
