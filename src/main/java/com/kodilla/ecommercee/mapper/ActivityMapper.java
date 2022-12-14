package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Activity;
import com.kodilla.ecommercee.domain.dto.ActivityDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityMapper {

    public ActivityDto mapToActivityDto(final Activity activity) {
        return new ActivityDto(
                activity.getUser().getUserId(),
                activity.getDateTime(),
                activity.getMessage()
        );
    }

    public List<ActivityDto> mapToActivityDtoList(final List<Activity> activities) {
        return activities.stream()
                .map(this::mapToActivityDto)
                .collect(Collectors.toList());
    }
}
