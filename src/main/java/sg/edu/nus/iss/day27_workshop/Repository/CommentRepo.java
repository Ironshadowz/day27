package sg.edu.nus.iss.day27_workshop.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.client.result.UpdateResult;

import sg.edu.nus.iss.day27_workshop.Model.Comment;

@Repository
public class CommentRepo 
{
    @Autowired
    private MongoTemplate template;

    public static final String C_REVIEW = "reviews";
    public static final String F_USER = "user";
    public static final String F_RATING = "rating";
    public static final String F_C_TEXT = "comment";
    public static final String F_GID = "ID";
    public static final String F_DATE = "posted";
    public static final String F_GAME = "name";
    public static final String F_EDITED = "edited";

    public void insertComment(Comment comment)
    {
        Document doc = new Document();
        doc.put(F_USER, comment.getUser());
        doc.put(F_RATING, comment.getRating());
        doc.put(F_C_TEXT, comment.getC_text());
        doc.put(F_GID, comment.getGid());
        doc.put(F_DATE, comment.getPosted());
        doc.put(F_GAME, comment.getGameName());
        System.out.println(comment.getC_text());
        System.out.println(comment.getGid());
        template.insert(doc, C_REVIEW);
    }
    
    public void updateComment(Comment comment, String id)
    {            
        ObjectId docId = new ObjectId(id);
        Document edited = template.findById(docId, Document.class, C_REVIEW);   
        Document oldEdit = edited.get("edited", Document.class);
        LinkedList<HashMap<String, String>> editList = new LinkedList<>();
        HashMap<String, String> list = new HashMap<>();
        if(oldEdit==null)
        {
            System.out.println(oldEdit);
            //Add new edits to list
            list.put(F_RATING, edited.getString(F_RATING));
            list.put(F_C_TEXT, edited.getString(F_C_TEXT));
            list.put(F_DATE, edited.getString(F_DATE));
            editList.add(0, list);
            System.out.println("1");
            Query query = Query.query(Criteria.where("_id").is(docId));
            Update update = new Update()
                            .set(F_RATING, comment.getRating())
                            .set(F_C_TEXT, comment.getC_text())
                            .set(F_DATE, java.time.LocalTime.now())
                            .set(F_EDITED, editList);
            UpdateResult updateResult = template.upsert(query, update, Document.class, C_REVIEW);
            System.out.println(updateResult.getModifiedCount());
        } else
        {   
             System.out.println(oldEdit.toString());
            //Add old edits to list
                 System.out.println("Not null");           
                String oldComment = oldEdit.getString(F_C_TEXT);
                String oldRating = oldEdit.getString(F_RATING);
                String oldPosted = oldEdit.getString(F_DATE);
                list.put(F_C_TEXT, oldComment);
                list.put(F_RATING, oldRating);
                list.put(F_DATE, oldPosted);
                editList.add(0, list);
                System.out.println(editList.toString());
            
            //Add newest comment and rating to edit list
            list.put(F_RATING, comment.getRating().toString());
            list.put(F_C_TEXT, comment.getC_text());
            list.put(F_DATE, java.time.LocalTime.now().toString());
            editList.add(oldEdit.size()+1, list);
            Query query = Query.query(Criteria.where("_id").is(docId));
            Update update = new Update()
                            .set(F_RATING, comment.getRating())
                            .set(F_C_TEXT, comment.getC_text())
                            .set(F_DATE, java.time.LocalTime.now())
                            .set(F_EDITED, editList);
            UpdateResult updateResult = template.upsert(query, update, Document.class, C_REVIEW);
        }
    }
}
