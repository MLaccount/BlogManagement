package com.kickcity.task.blogmanagement.controller;

import com.kickcity.task.blogmanagement.common.Utils;
import com.kickcity.task.blogmanagement.exception.NoContentFoundException;
import com.kickcity.task.blogmanagement.exception.ResourceAlreadyExistException;
import com.kickcity.task.blogmanagement.model.Record;
import com.kickcity.task.blogmanagement.model.User;
import com.kickcity.task.blogmanagement.repository.RecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RecordController {


    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RecordRepository recordRepository;

    @PostMapping("/record/create")
    public ResponseEntity<?> addRecord(@RequestBody Record record) {
        logger.info("Creating Record : {}", record);

        recordRepository.save(record);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @GetMapping("/record/get/{id}")
    public ResponseEntity<?> getRecordById(@PathVariable(value = "id") long recordId) {
        logger.info("Fetching Record with id {}", recordId);
        Optional<Record> recordOptional = recordRepository.findById(recordId);

        if (!recordOptional.isPresent()) {
            logger.error("Record with id {} not found.", recordId);
            throw new EntityNotFoundException("Record with id " + recordId + " not found");
        }
        return new ResponseEntity<Record>(recordOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/record/get")
    public List<Record> getRecord() {
        List<Record> recordList = recordRepository.findAll();
        if (recordList.isEmpty()) {
            throw new NoContentFoundException("No records found");
        }
        return recordList;
    }

    @RequestMapping(value = "/record/update/{id}", method = RequestMethod.PUT)
    public Record updateRecord(@PathVariable("id") long id, @RequestBody Record record) {
        logger.info("Updating User with id {}", id);
        Record currentRecord = recordRepository.getOne(id);

        currentRecord.setCreateDate(Utils.getCurrentDate());
        currentRecord.setText(record.getText());
        currentRecord.setTitle(record.getTitle());
        currentRecord.setId(id);

        recordRepository.save(currentRecord);
        return currentRecord;
    }

    @RequestMapping(value = "/record/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRecord(@PathVariable("id") long id) {
        Optional<Record> userOptional = recordRepository.findById(id);
        if (!userOptional.isPresent()) {
            logger.error("Record with id {} not found.", id);
            throw new EntityNotFoundException("Record with id " + id + " not found");
        }
        recordRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
