package com.example.polls.controller;

import com.example.polls.model.Pomodoro;
import com.example.polls.service.PomodoroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/pomodoros")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PomodoroController {
    @Autowired
    private PomodoroService pomodoroService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Page<Pomodoro> getPage(@RequestParam int page, @RequestParam int pageSize) {
        return pomodoroService.getPage(page, pageSize);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Pomodoro getOne(@PathVariable Long id) {
        return pomodoroService.getOne(id);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Pomodoro create(@Valid @RequestBody Pomodoro pomodoro) throws NullPointerException{
        return pomodoroService.create(pomodoro);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public Pomodoro update(@RequestBody Pomodoro pomodoroRequestBody,
                       @PathVariable Long id) {
        return pomodoroService.update(pomodoroRequestBody, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public HttpEntity delete(@PathVariable("id") long id){
        return pomodoroService.delete(id);
    }
}
