package cn.lsr.noveladmin.model;

public class TypeBookKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column type-book.type_id
     *
     * @mbg.generated Fri Nov 05 11:46:01 CST 2021
     */
    private Integer typeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column type-book.book_id
     *
     * @mbg.generated Fri Nov 05 11:46:01 CST 2021
     */
    private Long bookId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column type-book.type_id
     *
     * @return the value of type-book.type_id
     *
     * @mbg.generated Fri Nov 05 11:46:01 CST 2021
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column type-book.type_id
     *
     * @param typeId the value for type-book.type_id
     *
     * @mbg.generated Fri Nov 05 11:46:01 CST 2021
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column type-book.book_id
     *
     * @return the value of type-book.book_id
     *
     * @mbg.generated Fri Nov 05 11:46:01 CST 2021
     */
    public Long getBookId() {
        return bookId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column type-book.book_id
     *
     * @param bookId the value for type-book.book_id
     *
     * @mbg.generated Fri Nov 05 11:46:01 CST 2021
     */
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}