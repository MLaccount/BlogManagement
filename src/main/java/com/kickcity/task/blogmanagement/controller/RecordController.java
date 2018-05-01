package com.kickcity.task.blogmanagement.controller;

import com.kickcity.task.blogmanagement.model.Record;
import com.kickcity.task.blogmanagement.model.User;
import com.kickcity.task.blogmanagement.service.RecordService;
import com.kickcity.task.blogmanagement.service.UserService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/records")
public class RecordController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RecordService recordService;

    @Autowired
    private UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getRecordById(@PathVariable(value = "id") long recordId) {
        logger.info("Fetching Record with id {}", recordId);
        Record record = recordService.findRecordById(recordId);
        return new ResponseEntity<Record>(record, HttpStatus.OK);
    }

    @GetMapping("")
    public List<Record> getRecords() {
        return recordService.findAllRecords();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Record updateRecord(@PathVariable("id") long id, @RequestBody Record record) {
        logger.info("Updating record with id {}", id);
        record.setId(id);
        return recordService.updateRecord(record);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRecord(@PathVariable("id") long id) {
        recordService.deleteRecordById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
