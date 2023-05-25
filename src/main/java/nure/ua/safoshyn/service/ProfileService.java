package nure.ua.safoshyn.service;

import nure.ua.safoshyn.entity.Profile;

public interface ProfileService {
    Profile getProfile(Long id);
    Profile getProfile(String username);
    Profile updateProfile(Long id);
    Profile saveProfile(Profile user);
}
