package cn.lsr.noveladmin.RequestParam;

import cn.lsr.noveladmin.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookParam {
    private Long id;
    private Boolean workDirection;
    private String picUrl;
    private String bookName;
    private Long authorId;
    private String authorName;
    private String bookDesc;
    private Float score;
    private Boolean bookStatus;
    private Long visitCount;
    private Integer wordCount;
    private Integer commentCount;
    private Long indexId;
    private String indexName;
    private Date lastIndexUpdateTime;
    private Boolean isVip;
    private Boolean status;
    private Date createTime;
    private Boolean isRecommend;
    private String typeName;
    private Float minScore;
    private Float maxScore;
    private String key;
}
