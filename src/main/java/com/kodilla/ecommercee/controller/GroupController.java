package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.dto.GroupDto;
import com.kodilla.ecommercee.exception.GroupNotFoundException;
import com.kodilla.ecommercee.mapper.GroupMapper;
import com.kodilla.ecommercee.service.GroupDbService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/v1/groups")
public class GroupController {

    @Autowired
    private GroupDbService groupDbService;

    @Autowired
    private GroupMapper groupMapper;

    @GetMapping
    public ResponseEntity<List<GroupDto>> getGroups() {
        List<Group> groups = groupDbService.getAllGroups();
        return ResponseEntity.ok(groupMapper.mapToGroupDtoList(groups));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addGroup(@RequestBody GroupDto groupDto) {
        Group group = groupMapper.mapToGroup(groupDto);
        groupDbService.saveGroup(group);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{groupId}")
    public ResponseEntity<GroupDto> getGroup(@PathVariable Long groupId) throws GroupNotFoundException{
        return ResponseEntity.ok(groupMapper.mapToGroupDto(groupDbService.getGroup(groupId)));
    }

    @PutMapping
    public ResponseEntity<GroupDto> updateGroup(@RequestBody GroupDto groupDto) {
        Group group = groupMapper.mapToGroup(groupDto);
        Group saveGroup = groupDbService.saveGroup(group);
        return ResponseEntity.ok(groupMapper.mapToGroupDto(saveGroup));

    }
}
