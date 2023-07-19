package sg.edu.nus.iss.day27_workshop.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment 
{
    private String user;
    private Integer rating;
    private String c_text;
    private Integer gid;
    private String posted;
    private String gameName;
}
