package com.kickcity.task.blogmanagement.controller;

import com.kickcity.task.blogmanagement.exception.NoContentFoundException;
import com.kickcity.task.blogmanagement.model.Record;
import com.kickcity.task.blogmanagement.service.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RecordService recordService;

    @PostMapping("")
    public ResponseEntity<?> addRecord(@RequestBody Record record) {
        logger.info("Creating Record : {}", record);
        recordService.saveRecord(record);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecordById(@PathVariable(value = "id") long recordId) {
        logger.info("Fetching Record with id {}", recordId);
        Optional<Record> recordOptional = recordService.findRecordById(recordId);
        if (!recordOptional.isPresent()) {
            logger.error("Record with id {} not found.", recordId);
            throw new EntityNotFoundException("Record with id " + recordId + " not found");
        }
        return new ResponseEntity<Record>(recordOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/list")
    public List<Record> getRecord() {
        List<Record> recordList = recordService.findAllRecords();
        if (recordList.isEmpty()) {
            throw new NoContentFoundException("No records found");
        }
        return recordList;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Record updateRecord(@PathVariable("id") long id, @RequestBody Record record) {
        logger.info("Updating User with id {}", id);
        record.setId(id);
        return recordService.updateRecord(record);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRecord(@PathVariable("id") long id) {
        recordService.deleteRecordById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
