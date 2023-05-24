package nure.ua.safoshyn.diveLicencePro.service;

import lombok.AllArgsConstructor;
import nure.ua.safoshyn.diveLicencePro.entity.CustomUser;
import nure.ua.safoshyn.diveLicencePro.exception.EntityNotFoundException;
import nure.ua.safoshyn.diveLicencePro.repository.CustomUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomUserServiceImpl implements CustomUserService {
    private CustomUserRepository customUserRepository;

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
        return null; //TODO:adding to dive club
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
