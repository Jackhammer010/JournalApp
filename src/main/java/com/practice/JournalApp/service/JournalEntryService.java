package com.practice.JournalApp.service;

import com.practice.JournalApp.entity.JournalEntry;
import com.practice.JournalApp.entity.User;
import com.practice.JournalApp.repository.JournalEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JournalEntryService {

    private final UserService userService;
    private final JournalEntryRepository journalRepository;

    public List<JournalEntry> getAllEntriesOfUser(String username){
        User user = userService.findUserByUsername(username);
        return user.getJournalEntries();
    }
    @Transactional
    public void addNewEntry(JournalEntry journalEntry, String username){
        User user = userService.findUserByUsername(username);
        JournalEntry saved = journalRepository.insert(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
    }
}
