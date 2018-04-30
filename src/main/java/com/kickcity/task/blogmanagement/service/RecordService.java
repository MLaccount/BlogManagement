package com.kickcity.task.blogmanagement.service;

import com.kickcity.task.blogmanagement.model.Record;

import java.util.List;
import java.util.Optional;

public interface RecordService {

    Optional<Record> findRecordById(Long id);

    void saveRecord(Record record);

    Record updateRecord(Record record);

    void deleteRecordById(Long id);

    List<Record> findAllRecords();

}
