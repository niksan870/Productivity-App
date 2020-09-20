package com.example.polls.service;

import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.PomodoroMusic;
import com.example.polls.repository.PomodoroMusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PomodoroMusicService {
    @Autowired
    private PomodoroMusicRepository pomodoroMusicRepository;

    @Autowired
    private UserPrincipal userPrincipal;

    public Page<PomodoroMusic> getPage(Pageable pageable) {
        return pomodoroMusicRepository.findAll(pageable);
    }

    public PomodoroMusic getOne(Long id) {
        return pomodoroMusicRepository.findOneById(id);
    }

    public PomodoroMusic create(PomodoroMusic pomodoroMusic) throws NullPointerException {
//        pomodoroMusic.setUser(userPrincipal.getCurrentUserPrincipal());
        return pomodoroMusicRepository.save(pomodoroMusic);
    }

    public PomodoroMusic update(PomodoroMusic pomodoroMusic, Long id) {
        PomodoroMusic pomodoro = pomodoroMusicRepository.findOneById(id);
        pomodoro.setTitle(pomodoroMusic.getTitle());
        pomodoro.setUrl(pomodoroMusic.getUrl());

        return pomodoroMusicRepository.save(pomodoro);
    }

    public HttpEntity delete(Long id) {
        PomodoroMusic pomodoroMusic = pomodoroMusicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pomodoro was not found for this id :: " + id));

        if (pomodoroMusic.getId() == userPrincipal.getCurrentUserPrincipal().getId()) {
            pomodoroMusicRepository.delete(pomodoroMusic);
            return new ResponseEntity("Deleted", HttpStatus.OK);
        }

        return new ResponseEntity("Ops, you are not authenticated ", HttpStatus.UNAUTHORIZED);
    }
}
