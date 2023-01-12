package com.brian.xflfantasy.controllers;


import com.brian.xflfantasy.models.Owner;
import com.brian.xflfantasy.models.Player;
import com.brian.xflfantasy.services.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/owners")
@CrossOrigin("*")
@RequiredArgsConstructor

public class OwnerController {
    @Autowired
    private final OwnerService ownerService;

    @PostMapping
    public Owner createOwner(@RequestBody Owner owner){
        return ownerService.createOwner(owner);
    }

    @DeleteMapping("/{id}")
    public void deleteOwner(@PathVariable("id") int id){
        ownerService.deleteOwnerById(id);
    }

    @PatchMapping("/{id}")
    public Owner updateOwner(@PathVariable("id") int id, @RequestBody Owner owner){
        return ownerService.updateOwnerById(id, owner);
    }

    @GetMapping
    public List<Owner> getOwners(){
        return ownerService.getAllOwners();
    }

    @GetMapping("/{id}")
    public Owner getOwnerById(@PathVariable("id") int id){
        return ownerService.getOwnerById(id);
    }

    @GetMapping("/{username}/{password}")
    public Owner getOwnerByUsernameAndPassword(@PathVariable("username") String username, @PathVariable("password") String password){
        var owners = ownerService.getAllOwners();
        for (Owner owner: owners) {
            if (Objects.equals(owner.getUsername(), username)) {
                if (Objects.equals(owner.getPassword(), password)) {
                    return owner;
                }
            }
        }
        var owner = new Owner();
        return owner;
    }
}
