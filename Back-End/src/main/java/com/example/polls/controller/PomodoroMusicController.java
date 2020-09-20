package com.example.polls.controller;

import com.example.polls.model.PomodoroMusic;
import com.example.polls.service.PomodoroMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/music")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PomodoroMusicController {
  @Autowired private PomodoroMusicService pomodoroMusicService;

  @GetMapping("")
  @PreAuthorize("isAuthenticated()")
  @ResponseStatus(HttpStatus.OK)
  public Page<PomodoroMusic> getPage(@RequestParam int page, @RequestParam int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return pomodoroMusicService.getPage(pageable);
  }

  @GetMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  @ResponseStatus(HttpStatus.OK)
  public PomodoroMusic getOne(@PathVariable Long id) {
    return pomodoroMusicService.getOne(id);
  }

  @PostMapping("")
  @PreAuthorize("isAuthenticated()")
  @ResponseStatus(HttpStatus.CREATED)
  public PomodoroMusic create(@RequestBody PomodoroMusic pomodoroMusic)
      throws NullPointerException {
    return pomodoroMusicService.create(pomodoroMusic);
  }

  @PutMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  @ResponseStatus(HttpStatus.OK)
  public PomodoroMusic update(
      @RequestBody PomodoroMusic pomodoroRequestBody, @PathVariable Long id) {
    return pomodoroMusicService.update(pomodoroRequestBody, id);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  @ResponseStatus(HttpStatus.OK)
  public HttpEntity delete(@PathVariable("id") long id) {
    return pomodoroMusicService.delete(id);
  }
}
