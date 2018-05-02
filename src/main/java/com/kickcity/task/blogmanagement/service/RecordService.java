package com.kickcity.task.blogmanagement.service;

import com.kickcity.task.blogmanagement.model.Record;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecordService {

    Record findRecordById(Long id);

    Record saveRecord(Record record);

    Record updateRecord(Record record);

    void deleteRecordById(Long id);

    List<Record> findAllRecords();

    List<Record> findAllByUsers(Pageable pageRequest);

}
