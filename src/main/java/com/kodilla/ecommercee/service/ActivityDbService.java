package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Activity;
import com.kodilla.ecommercee.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityDbService {

    private final ActivityRepository activityRepository;

    public List<Activity> getAllActivities(Long userId) {
        return activityRepository.findAll().stream()
                .filter(a -> a.getUser().getUserId() == userId)
                .collect(Collectors.toList());
    }

    public Activity saveActivity(final Activity activity) {
        return activityRepository.save(activity);
    }
}
