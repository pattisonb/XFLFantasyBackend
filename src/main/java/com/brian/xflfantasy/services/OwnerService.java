package com.brian.xflfantasy.services;

import com.brian.xflfantasy.models.Owner;
import com.brian.xflfantasy.repositories.OwnerRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class OwnerService {

    @Autowired
    private final OwnerRepo ownerRepo;

    public Owner createOwner(Owner owner){
        return ownerRepo.save(owner);
    }

    public void deleteOwnerById(int ownerId){
        ownerRepo.deleteById(ownerId);
    }

    public Owner updateOwnerById(int ownerId, Owner owner){
        owner.setId(ownerId);
        return ownerRepo.save(owner);
    }

    public List<Owner> getAllOwners(){
        return ownerRepo.findAll();
    }
    public Owner getOwnerById(int ownerId){
        return ownerRepo.getReferenceById(ownerId);
    }
}
