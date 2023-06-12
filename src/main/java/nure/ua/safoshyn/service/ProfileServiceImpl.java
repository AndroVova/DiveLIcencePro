package nure.ua.safoshyn.service;

import lombok.AllArgsConstructor;
import nure.ua.safoshyn.entity.Profile;
import nure.ua.safoshyn.entity.Role;
import nure.ua.safoshyn.exception.EntityNotFoundException;
import nure.ua.safoshyn.repository.ProfileRepository;
import nure.ua.safoshyn.repository.RoleRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService{
    private ProfileRepository profileRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepository roleRepository;

    @Override
    public Profile getProfile(Long id) {
        Optional<Profile> profile = profileRepository.findById(id);
        return unwrapProfile(profile, id);
    }

    @Override
    public Profile getProfile(String username) {
        Optional<Profile> user = profileRepository.findByEmail(username);
        return unwrapProfile(user, 404L);
    }

    //ToDo:update profile
    @Override
    public Profile updateProfile(Long id) {
        return null;
    }

    @Override
    public Profile saveProfile(Profile request) {
        Profile profile = getProfileCredentials(request);

        profile.addRole(roleRepository.findByName("USER"));
        return profileRepository.save(profile);
    }

    @Override
    public Profile saveAdminProfile(Profile request) {
        Profile profile = getProfileCredentials(request);

        profile.addRole(roleRepository.findByName("ADMIN"));
        profile.addRole(roleRepository.findByName("USER"));
        return profileRepository.save(profile);
    }

    private Profile getProfileCredentials(Profile request) {
        Profile profile = new Profile();
        profile.setEmail(request.getEmail());
        profile.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        return profile;
    }

    public static Profile unwrapProfile(Optional<Profile> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Profile.class);
    }
}
