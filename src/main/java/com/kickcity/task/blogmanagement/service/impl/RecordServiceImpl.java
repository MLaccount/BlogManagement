package com.kickcity.task.blogmanagement.service.impl;

import com.kickcity.task.blogmanagement.common.Utils;
import com.kickcity.task.blogmanagement.common.Validator;
import com.kickcity.task.blogmanagement.exception.NoContentFoundException;
import com.kickcity.task.blogmanagement.model.Record;
import com.kickcity.task.blogmanagement.repository.RecordRepository;
import com.kickcity.task.blogmanagement.service.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {

    private static final Logger logger = LoggerFactory.getLogger(RecordServiceImpl.class);

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private Validator validator;


    @Override
    public Record findRecordById(Long id) {
        validator.checkForNull(id, "Record id");
        logger.info("Fetching record with id {}", id);

        Optional<Record> recordOptional = recordRepository.findById(id);
        if (!recordOptional.isPresent()) {
            logger.error("Record with id {} not found.", id);
            throw new EntityNotFoundException("Record with id " + id + " not found");
        }
        return recordOptional.get();
    }

    @Transactional
    @Override
    public Record saveRecord(Record record) {
        validator.checkForNull(record, "Record");
        validator.checkForNull(record.getTitle(), "Title");
        logger.info("Creating Record : {}", record);

        if (record.getId() == null) {
            record.setCreateDate(Utils.getCurrentDate());
        }
        return recordRepository.save(record);
    }

    @Transactional
    @Override
    public Record updateRecord(Record record) {

        validator.checkForNull(record, "Record");
        validator.checkForNull(record.getId(), "Record id");
        // Check that record with given id exists
        Record existingRecord = findRecordById(record.getId());
        // Do not update creation date
        record.setCreateDate(existingRecord.getCreateDate());
        return saveRecord(record);
    }

    @Transactional
    @Override
    public void deleteRecordById(Long id) {
        validator.checkForNull(id, "record id");
        findRecordById(id);
        recordRepository.deleteById(id);
    }

    @Override
    public List<Record> findAllRecords() {
        List<Record> recordList = recordRepository.findAll(new Sort(Sort.Direction.DESC, "createDate"));
        if (recordList.isEmpty()) {
            throw new NoContentFoundException("No records found");
        }
        return recordList;
    }

    @Override
    public List<Record> findAllByUsers(Pageable pageRequest) {
        return recordRepository.findAllByUsers(pageRequest);
    }

}
