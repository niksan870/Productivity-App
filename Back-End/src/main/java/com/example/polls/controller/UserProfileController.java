package com.example.polls.controller;

import com.example.polls.dto.user.UserProfileDTO;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.User;
import com.example.polls.payload.UserSummary;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.UserProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@RestController
@RequestMapping("api/profiles")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserProfileController {
  @Autowired private UserProfileService userProfileService;

  @GetMapping("")
  @PreAuthorize("isAuthenticated()")
  @ResponseStatus(HttpStatus.OK)
  public Page<UserProfileDTO> getMany(@RequestParam int page, @RequestParam int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return userProfileService.getMany(pageable);
  }

  @GetMapping("me")
  @PreAuthorize("hasRole('USER')")
  public UserProfileDTO getCurrentUser(@CurrentUser UserPrincipal currentUser) {
    return userProfileService.getOne(currentUser.getId());
  }

  @PostMapping("")
  @PreAuthorize("isAuthenticated()")
  @ResponseStatus(HttpStatus.CREATED)
  public User create(@RequestBody User newUserProfile) {
    return userProfileService.newUser(newUserProfile);
  }

  @GetMapping("{id}")
  @PreAuthorize("isAuthenticated()")
  @ResponseStatus(HttpStatus.OK)
  public UserProfileDTO getOne(@PathVariable long id) {
    return userProfileService.getOne(id);
  }

  @GetMapping("/getParticipants/{id}")
  @PreAuthorize("isAuthenticated()")
  @ResponseStatus(HttpStatus.OK)
  public Page<UserProfileDTO> getParticipants(@PathVariable UUID id)
      throws JsonProcessingException {
    return userProfileService.getParticipants(id);
  }

  @GetMapping("/getGoalOwner/{id}")
  @PreAuthorize("isAuthenticated()")
  @ResponseStatus(HttpStatus.OK)
  public Page<UserProfileDTO> getGoalOwner(
      @RequestParam int page, @RequestParam int pageSize, @PathVariable UUID id) {
    return userProfileService.getGoalOwner(id);
  }

  @PutMapping("{id}")
  @PreAuthorize("isAuthenticated()")
  @ResponseStatus(HttpStatus.OK)
  public void update(@PathVariable Long id, @RequestBody UserProfileDTO userProfileBody)
      throws ResourceNotFoundException, IOException {
    userProfileService.update(id, userProfileBody);
  }

  @DeleteMapping("{usersProfileToBeDeletedId}")
  @PreAuthorize("isAuthenticated()")
  @ResponseStatus(HttpStatus.OK)
  public HttpEntity delete(
      @PathVariable Long usersProfileToBeDeletedId, HttpServletRequest request) {
    return userProfileService.delete(usersProfileToBeDeletedId);
  }
}
