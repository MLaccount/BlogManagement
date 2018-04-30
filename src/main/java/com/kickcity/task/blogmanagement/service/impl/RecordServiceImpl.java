package com.kickcity.task.blogmanagement.service.impl;

import com.kickcity.task.blogmanagement.common.Utils;
import com.kickcity.task.blogmanagement.model.Record;
import com.kickcity.task.blogmanagement.repository.RecordRepository;
import com.kickcity.task.blogmanagement.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import org.springframework.util.Assert;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public Optional<Record> findRecordById(Long id) {
        Assert.notNull(id, "Record id must not be null");
        return recordRepository.findById(id);
    }

    @Transactional
    @Override
    public void saveRecord(Record record) {
        Assert.notNull(record, "Record must not be null");
        Assert.isNull(record.getId(), "New record must be provided at creation");
        record.setCreateDate(Utils.getCurrentDate());
        recordRepository.save(record);
    }

    @Transactional
    @Override
    public Record updateRecord(Record record) {
        Assert.notNull(record, "Record must not be null");
        Assert.notNull(record.getId(), "Existing record must be provided at update");
        // Check that record with given id exists
        Record existingRecord = findRecordById(record.getId()).get();
        // Do not update creation date
        record.setCreateDate(existingRecord.getCreateDate());
        return recordRepository.save(record);
    }

    @Transactional
    @Override
    public void deleteRecordById(Long id) {
        Assert.notNull(id, "Record id must not be null");
        recordRepository.deleteById(id);
    }

    @Override
    public List<Record> findAllRecords() {
        return recordRepository.findAll(new Sort(Sort.Direction.DESC, "createDate"));
    }

}
