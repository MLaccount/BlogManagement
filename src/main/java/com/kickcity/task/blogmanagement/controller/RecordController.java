package com.kickcity.task.blogmanagement.controller;

import com.kickcity.task.blogmanagement.model.Record;
import com.kickcity.task.blogmanagement.model.User;
import com.kickcity.task.blogmanagement.model.dto.RecordDto;
import com.kickcity.task.blogmanagement.service.RecordService;
import com.kickcity.task.blogmanagement.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RecordService recordService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<?> createRecord(@RequestBody RecordDto recordDto) {
        logger.info("Creating Record : {}", recordDto);

        User user = userService.findUserById(recordDto.getUserId());
        Record record = modelMapper.map(recordDto, Record.class);
        record.setUser(user);
        recordService.saveRecord(record);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecordById(@PathVariable(value = "id") long recordId) {
        logger.info("Fetching Record with id {}", recordId);
        RecordDto recordDto = modelMapper.map(recordService.findRecordById(recordId), RecordDto.class);

        return new ResponseEntity<RecordDto>(recordDto, HttpStatus.OK);
    }

    @GetMapping("")
    public List<RecordDto> getRecords() {
        List<Record> recordList = recordService.findAllRecords();
        return recordList.stream().map(record -> modelMapper.map(record, RecordDto.class))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Record updateRecord(@PathVariable("id") long id, @RequestBody RecordDto recordDto) {
        logger.info("Updating record with id {}", id);
        Record record = modelMapper.map(recordDto, Record.class);
        record.setId(id);
        return recordService.updateRecord(record);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRecord(@PathVariable("id") long id) {
        recordService.deleteRecordById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
