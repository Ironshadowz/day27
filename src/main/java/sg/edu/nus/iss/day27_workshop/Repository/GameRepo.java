package sg.edu.nus.iss.day27_workshop.Repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepo 
{
    @Autowired
    MongoTemplate template;

    public static final String C_GAME = "game";

     public Boolean checkGid(Integer id)
    {
        Query query = Query.query(Criteria.where("gid").is(id));
        List<Document> docs = template.find(query, Document.class, C_GAME);
        int x = docs.size();
        System.out.println(x);
        return x>0 ? true:false;
    }
}
