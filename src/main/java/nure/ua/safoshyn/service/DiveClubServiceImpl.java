package nure.ua.safoshyn.service;

import lombok.AllArgsConstructor;
import nure.ua.safoshyn.entity.CustomUser;
import nure.ua.safoshyn.entity.DiveClub;
import nure.ua.safoshyn.exception.EntityNotFoundException;
import nure.ua.safoshyn.repository.DiveClubRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class DiveClubServiceImpl implements DiveClubService {
    DiveClubRepository diveClubRepository;

    public DiveClub getDiveClub(Long id){
        return null;
    }


    public static DiveClub unwrapDiveClub(Optional<DiveClub> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, CustomUser.class);
    }
}
