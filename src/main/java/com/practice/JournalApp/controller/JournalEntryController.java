package com.practice.JournalApp.controller;

import com.practice.JournalApp.entity.JournalEntry;
import com.practice.JournalApp.service.JournalEntryService;
import com.practice.JournalApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/journal-entry")
public class JournalEntryController {

    private final JournalEntryService journalService;
    private final UserService userService;

    @GetMapping
    public List<JournalEntry> getAllEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return journalService.getAllEntriesOfUser(username);
    }
    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable String id){
        ObjectId entryId = new ObjectId(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<JournalEntry> entry = journalService.findById(username, entryId);

        return entry.map(journalEntry -> new ResponseEntity<>(journalEntry, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry entry){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalService.addNewEntry(entry, username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            log.error("Error adding journal entry: ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/entryId/{id}")
    public ResponseEntity<?> updateEntryById(@PathVariable String id, @RequestBody JournalEntry entry){
        ObjectId entryId = new ObjectId(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return journalService.updateEntryById(username, entryId, entry);
    }
    @DeleteMapping("entryId/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ObjectId entryId = new ObjectId(id);
        var removed = journalService.deleteEntryById(entryId, username);
        return (removed) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
