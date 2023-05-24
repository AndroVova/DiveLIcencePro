package nure.ua.safoshyn.diveLicencePro.service;

import nure.ua.safoshyn.diveLicencePro.entity.CustomUser;

import java.util.List;

public interface CustomUserService {
    CustomUser getCustomUser(Long id);
    CustomUser saveCustomUser(CustomUser customUser);
    CustomUser addCustomUserToNewDiveClub(Long customUserId, Long diveClubId);
    void deleteCustomUser(Long id);
    List<CustomUser> getCustomUsers();
}
