package sg.edu.nus.iss.day27_workshop.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.day27_workshop.Model.Comment;
import sg.edu.nus.iss.day27_workshop.Repository.CommentRepo;
import sg.edu.nus.iss.day27_workshop.Repository.GameRepo;

@Service
public class CommentService 
{
    @Autowired
    CommentRepo reviewRepo;
    @Autowired
    GameRepo gameRepo;

    public void insertReview(Comment com)
    {
        reviewRepo.insertComment(com);
    }
    public boolean checkGid(Integer id)
    {
       return gameRepo.checkGid(id);
    }
    public void updateComment(Comment comment, String id)
    {
        reviewRepo.updateComment(comment, id);
    }
}
