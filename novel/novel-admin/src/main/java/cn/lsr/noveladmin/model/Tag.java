package cn.lsr.noveladmin.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class Tag {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tag.id
     *
     * @mbg.generated Fri Nov 05 11:46:36 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tag.tag_name
     *
     * @mbg.generated Fri Nov 05 11:46:36 CST 2021
     */
    @NotEmpty(message = "标签不能为空")
    @Length(max = 36, message = "标签名最大长度为36")
    private String tagName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tag.create_time
     *
     * @mbg.generated Fri Nov 05 11:46:36 CST 2021
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tag.id
     *
     * @return the value of tag.id
     *
     * @mbg.generated Fri Nov 05 11:46:36 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tag.id
     *
     * @param id the value for tag.id
     *
     * @mbg.generated Fri Nov 05 11:46:36 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tag.tag_name
     *
     * @return the value of tag.tag_name
     *
     * @mbg.generated Fri Nov 05 11:46:36 CST 2021
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tag.tag_name
     *
     * @param tagName the value for tag.tag_name
     *
     * @mbg.generated Fri Nov 05 11:46:36 CST 2021
     */
    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tag.create_time
     *
     * @return the value of tag.create_time
     *
     * @mbg.generated Fri Nov 05 11:46:36 CST 2021
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tag.create_time
     *
     * @param createTime the value for tag.create_time
     *
     * @mbg.generated Fri Nov 05 11:46:36 CST 2021
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
