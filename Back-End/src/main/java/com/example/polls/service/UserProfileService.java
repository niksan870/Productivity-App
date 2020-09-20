package com.example.polls.service;

import com.example.polls.dto.user.UserProfileDTO;
import com.example.polls.exception.BadRequestException;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Goal;
import com.example.polls.model.User;
import com.example.polls.repository.GoalChartRepository;
import com.example.polls.repository.GoalsRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public
class UserProfileService {

    @Autowired
    private UserPrincipal userPrincipal;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoalsRepository goalsRepository;

    @Autowired
    private GoalChartRepository goalChartRepository;

    public Page<UserProfileDTO> getMany(Pageable pageable) {
        User currentUser = this.userPrincipal.getCurrentUserPrincipal();
        Page<User> userProfiles;
        userProfiles = userRepository.findAll(pageable);

        List<User> profiles = userProfiles.getContent();
        List<UserProfileDTO> listOfPostDTO = ObjectMapperUtils.mapAll(profiles, UserProfileDTO.class);

        return new PageImpl<>(listOfPostDTO);
    }

    public Page<UserProfileDTO> getParticipants(UUID id) {
        User currentUser = this.userPrincipal.getCurrentUserPrincipal();

        Set<User> attendees = userRepository.getParticipants(id, currentUser.getId());
        List<UserProfileDTO> listOfPostDTO = ObjectMapperUtils.mapAll(attendees, UserProfileDTO.class);
        final Page<UserProfileDTO> page = new PageImpl<>(listOfPostDTO);

        return page;
    }

    public User newUser(User newUser) {
        return userRepository.save(newUser);
    }

    public void update(Long id, UserProfileDTO userProfileBody) {
        User userProfile = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Profile not found for this id :: " + id));

        User user = ObjectMapperUtils.map(userProfileBody, userProfile);

        userRepository.save(user);
    }

    public UserProfileDTO getOne(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        UserProfileDTO userProfileDTO = ObjectMapperUtils.map(user, UserProfileDTO.class);

        if (user.getId() == userPrincipal.getCurrentUserPrincipal().getId()) {
            userProfileDTO.setEditProfile(true);
        } else {
            userProfileDTO.setEditProfile(false);
        }
        return userProfileDTO;
    }

    public Page<UserProfileDTO> getGoalOwner(UUID id) {
        Goal goal = goalsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal", "id", id));
        User user = userRepository.findById(goal.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));


        List<User> users = new ArrayList();
        users.add(user);

        System.out.println("\n\n\n123123123123123");
        System.out.println(users.size());
        System.out.println("\n\n\n");
        List<UserProfileDTO> userProfileDTO = ObjectMapperUtils.mapAll(users, UserProfileDTO.class);


        return new PageImpl<>(userProfileDTO);
    }


    public HttpEntity delete(Long userId) {
        User userProfileToBeDeleted = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", userId));

        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        String username = currentAuth.getPrincipal().toString();
        User currentUser = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        ;

        if ((currentUser.getId() == userProfileToBeDeleted.getId()) || (currentUser.getRoles().toString().indexOf(
                "ADMIN") == 1)) {
            userRepository.save(userProfileToBeDeleted);
            return new ResponseEntity("Successfully deleted users profile", HttpStatus.OK);
        } else {
            return new ResponseEntity("Cannot Delete other user profiles!", HttpStatus.UNAUTHORIZED);
        }
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }
    }

}