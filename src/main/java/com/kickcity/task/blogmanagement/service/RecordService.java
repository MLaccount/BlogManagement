package com.kickcity.task.blogmanagement.service;

import com.kickcity.task.blogmanagement.model.Record;

import java.util.List;
import java.util.Optional;

public interface RecordService {

    Record findRecordById(Long id);

    Record saveRecord(Record record);

    Record updateRecord(Record record);

    void deleteRecordById(Long id);

    List<Record> findAllRecords();

}
