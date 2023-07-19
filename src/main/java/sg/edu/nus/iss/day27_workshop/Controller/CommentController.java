package sg.edu.nus.iss.day27_workshop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.day27_workshop.Model.Comment;
import sg.edu.nus.iss.day27_workshop.Service.CommentService;

@Controller
@RequestMapping("/home")
public class CommentController 
{
    @Autowired
    CommentService comServ;

    @GetMapping
    public ModelAndView getComment()
    {
        ModelAndView mav = new ModelAndView();
        Comment com = new Comment();
        mav.setViewName("home");
        mav.addObject("comment", com);
        return mav;
    }

    @PostMapping("/insert")
    public ModelAndView insertComment(@ModelAttribute Comment comment)
    {
        ModelAndView mav = new ModelAndView();
        if(comServ.checkGid(comment.getGid()))
        {
            try
            {
                comServ.insertReview(comment);
            } catch(Exception ex) 
            {
                mav.setViewName("errorInsert");
                mav.setStatus(HttpStatusCode.valueOf(404));
                return mav;
            }  
            mav.setViewName("inserted");
            mav.addObject("comment", comment);
            return mav;
        } else
        {
            mav.setViewName("errorInsert");
            mav.setStatus(HttpStatusCode.valueOf(404));
            mav.addObject("GID", "Game is not found");
            return mav;
        }
    }

    @GetMapping("/reviews/{id}")
    public ModelAndView update(@PathVariable ("id") String id, HttpSession session)
    {
        ModelAndView mav = new ModelAndView();
        session.setAttribute("id", id);
        mav.setViewName("update");
        Comment com = new Comment();
        mav.addObject("update", com);
        return mav;
    }

    @PostMapping("/reviews")
    public ModelAndView updateComment(@ModelAttribute Comment comment, HttpSession session)
    {
        ModelAndView mav = new ModelAndView();
        try
        {
            comServ.updateComment(comment, (String) session.getAttribute("id"));
        } catch(Exception ex)
        {
            mav.setViewName("errorInsert");
            mav.setStatus(HttpStatusCode.valueOf(404));
            mav.addObject("update", "Update Failed");
            return mav;
        }
        mav.setViewName("update");
        mav.addObject("updated", comment);
        return mav;
    }
}
