package com.example.polls.service;

import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Pomodoro;
import com.example.polls.repository.PomodoroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class PomodoroService {
    @Autowired
    private PomodoroRepository pomodoroRepository;

    @Autowired
    private UserPrincipal userPrincipal;

    public Page<Pomodoro> getPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Pomodoro> asd =  pomodoroRepository.findByCreatedBy(userPrincipal.getCurrentUserPrincipal().getId(), pageable);

        return asd;
    }

    public Pomodoro getOne(Long id) {
        return pomodoroRepository.findOneById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pomodoro", "Id", id));
    }

    public Pomodoro create(Pomodoro pomodoro) throws NullPointerException{
        return pomodoroRepository.save(pomodoro);
    }

    public Pomodoro update(Pomodoro pomodoroRequestBody, Long id) {
        Pomodoro pomodoro = pomodoroRepository.findOneById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pomodoro", "Id", id));
        pomodoro.setCurrent(pomodoroRequestBody.isCurrent());
        pomodoro.setTitle(pomodoroRequestBody.getTitle());
        pomodoro.setBreakLength(pomodoroRequestBody.getBreakLength());
        pomodoro.setSessionLength(pomodoroRequestBody.getSessionLength());

        return pomodoroRepository.save(pomodoro);
    }

    public HttpEntity delete(Long id){
        Pomodoro pomodoro = pomodoroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pomodoro", "Id", id));

        if (pomodoro.getCreatedBy() == userPrincipal.getCurrentUserPrincipal().getId()) {
            pomodoroRepository.delete(pomodoro);
            return new ResponseEntity("Deleted", HttpStatus.OK);
        }

        return new ResponseEntity("Ops, you are not authenticated ", HttpStatus.UNAUTHORIZED);
    }
}
