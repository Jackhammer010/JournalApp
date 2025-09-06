package com.practice.JournalApp.service;

import com.practice.JournalApp.entity.JournalEntry;
import com.practice.JournalApp.entity.User;
import com.practice.JournalApp.repository.JournalEntryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JournalEntryService {

    private final UserService userService;
    private final JournalEntryRepository journalRepository;

    public List<JournalEntry> getAllEntriesOfUser(String username){
        User user = userService.findUserByUsername(username);
        return user.getJournalEntries();
    }
    public Optional<JournalEntry> findById(String username, ObjectId entryId){
        User user = userService.findUserByUsername(username);
        List<JournalEntry> entryList = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(entryId)).toList();
        if (!entryList.isEmpty()){
            return journalRepository.findById(entryId);
        }
        return Optional.empty();
    }
    @Transactional
    public void addNewEntry(JournalEntry journalEntry, String username){
        User user = userService.findUserByUsername(username);
        JournalEntry saved = journalRepository.insert(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
    }
    @Transactional
    public ResponseEntity<?> updateEntryById(String username, ObjectId id, JournalEntry entry){
        try{
            Optional<JournalEntry> oldEntry = findById(username, id);
            if (oldEntry.isPresent()){
                oldEntry.get().setTitle((entry.getTitle() != null && !entry.getTitle().isEmpty()) ? entry.getTitle() : oldEntry.get().getTitle());
                oldEntry.get().setContent((entry.getContent() != null && !entry.getContent().isEmpty()) ? entry.getContent() : oldEntry.get().getContent());
                journalRepository.save(oldEntry.get());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e){
            log.error("Error! entry not found", e);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    public boolean deleteEntryById(ObjectId id, String username){
        boolean removed = false;
        try{
            User user = userService.findUserByUsername(username);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed){
                userService.saveUser(user);
                journalRepository.deleteById(id);
            }
            return removed;
        }catch (Exception e){
            log.error("Error occurred while deleting the journal entry", e);
            return removed;
        }
    }
}
