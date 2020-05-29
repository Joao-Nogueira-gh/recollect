package ua.tqs.ReCollect.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.tqs.ReCollect.model.Comment;

import ua.tqs.ReCollect.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepo;

    public Comment getCommentById(Long id){
        Optional<Comment> result = commentRepo.findById(id);

        return result.orElse(null);
    }

    public List<Comment> getAll(){
        return commentRepo.findAll();
    }
    public void save(Comment comment){
        commentRepo.save(comment);
    }
    public void deleteAll(){
        commentRepo.deleteAll();
    }
    @Transactional
	public void addNewComment(Comment comment) {
        save(comment);
    }
    @Transactional
    public void deleteComment(Comment comment){
        commentRepo.delete(comment);
    }
}