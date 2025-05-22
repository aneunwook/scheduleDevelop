package org.example.scheduledevelop.comment.repository;

import org.example.scheduledevelop.comment.entity.Comment;
import org.example.scheduledevelop.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByScheduleId(Long scheduleId);
}
