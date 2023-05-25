package nure.ua.safoshyn.service;

import nure.ua.safoshyn.entity.Certificate;
import nure.ua.safoshyn.entity.CustomUser;
import nure.ua.safoshyn.entity.DiveClub;

import java.util.List;

public interface DiveClubService {

    DiveClub getDiveClub(Long id);
    DiveClub saveDiveClub(DiveClub diveClub);
    void deleteDiveClub(Long id);
    List<DiveClub> getDiveClubs();
    List<CustomUser> getUsersInDiveClub(Long id);
    List<Certificate> getCertificatesGivenByDiveClub(Long id);
}
