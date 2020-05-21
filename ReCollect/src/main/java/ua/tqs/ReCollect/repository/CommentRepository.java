package ua.tqs.ReCollect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.tqs.ReCollect.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findAll();
}