package nure.ua.safoshyn.service;

import lombok.AllArgsConstructor;
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
public class CustomUserServiceImpl implements CustomUserService {
    private CustomUserRepository customUserRepository;
    private DiveClubRepository diveClubRepository;

    @Override
    public CustomUser getCustomUser(Long id) {
        Optional<CustomUser> customUser = customUserRepository.findById(id);
        return unwrapCustomUser(customUser, id);
    }

    @Override
    public CustomUser saveCustomUser(CustomUser customUser) {
        return customUserRepository.save(customUser);
    }

    @Override
    public CustomUser addCustomUserToNewDiveClub(Long customUserId, Long diveClubId) {
        CustomUser customUser = getCustomUser(customUserId);
        Optional<DiveClub> diveClub = diveClubRepository.findById(diveClubId);
        DiveClub unwrappedDiveClub = DiveClubServiceImpl.unwrapDiveClub(diveClub, diveClubId);
        customUser.setDiveClub(unwrappedDiveClub);

        return customUserRepository.save(customUser);
    }

    @Override
    public void deleteCustomUser(Long id) {
        customUserRepository.deleteById(id);
    }

    @Override
    public List<CustomUser> getCustomUsers() {
        return (List<CustomUser>)customUserRepository.findAll();
    }


    private CustomUser unwrapCustomUser(Optional<CustomUser> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, CustomUser.class);
    }
}
