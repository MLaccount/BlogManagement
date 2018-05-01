package com.kickcity.task.blogmanagement.repository;

import com.kickcity.task.blogmanagement.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

//    @Query("SELECT r.id, r.title, r.text, r.createDate, u.email FROM Record r INNER JOIN User u ON u.id = r.user ORDER BY r.createDate")
//    List<Record> findAllByUsers(Pageable pageRequest);

}
