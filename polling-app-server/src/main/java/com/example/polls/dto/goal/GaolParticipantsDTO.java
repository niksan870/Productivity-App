package com.example.polls.dto.goal;


import com.example.polls.dto.user.UserProfileDTO;

import java.util.List;

public class GaolParticipantsDTO {
    private UserProfileDTO userProfile;
    private List<UserProfileDTO> attendees;

    public GaolParticipantsDTO(UserProfileDTO userProfile, List<UserProfileDTO> attendees) {
        this.userProfile = userProfile;
        this.attendees = attendees;
    }

    public UserProfileDTO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileDTO userProfile) {
        this.userProfile = userProfile;
    }

    public List<UserProfileDTO> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<UserProfileDTO> attendees) {
        this.attendees = attendees;
    }
}
