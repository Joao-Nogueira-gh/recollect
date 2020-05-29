package ua.tqs.ReCollect.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Comment;
import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.CommentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    public void setUp() {

        commentRepository.deleteAll();

    }

    @AfterEach
    public void cleanUp() {

        commentRepository.deleteAll();

    }

    // @Test
    // public void whenUserAddsComment_itIsAddedToTheSystem() {

    //     Item item = new Item("Moeda", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
    //     User owner=new User("user", "user@email.com", "x", "123456789");

    //     Comment comment=new Comment("nice!", owner, item);

    //     commentService.addNewComment(comment);

    //     List<Comment> commentList=new ArrayList<>();
    //     commentList.add(comment);

    //     given(commentRepository.findAll()).willReturn(commentList);

    //     Comment retrieved = commentService.getAll().get(0);

    //     assertEquals(retrieved.getText(), comment.getText());
    //     assertEquals(retrieved.getUser(), comment.getUser());
    //     assertEquals(retrieved.getItem(), comment.getItem());

    // }

}