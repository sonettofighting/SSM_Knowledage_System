package com.tree.community.model;

public class Collection {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column collection.id
     *
     * @mbg.generated Tue Jun 30 23:42:30 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column collection.user_id
     *
     * @mbg.generated Tue Jun 30 23:42:30 CST 2020
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column collection.question_id
     *
     * @mbg.generated Tue Jun 30 23:42:30 CST 2020
     */
    private Long questionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column collection.folder_id
     *
     * @mbg.generated Tue Jun 30 23:42:30 CST 2020
     */
    private Long folderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column collection.gmt_create
     *
     * @mbg.generated Tue Jun 30 23:42:30 CST 2020
     */
    private Long gmtCreate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column collection.id
     *
     * @return the value of collection.id
     *
     * @mbg.generated Tue Jun 30 23:42:30 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column collection.id
     *
     * @param id the value for collection.id
     *
     * @mbg.generated Tue Jun 30 23:42:30 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column collection.user_id
     *
     * @return the value of collection.user_id
     *
     * @mbg.generated Tue Jun 30 23:42:30 CST 2020
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column collection.user_id
     *
     * @param userId the value for collection.user_id
     *
     * @mbg.generated Tue Jun 30 23:42:30 CST 2020
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column collection.question_id
     *
     * @return the value of collection.question_id
     *
     * @mbg.generated Tue Jun 30 23:42:30 CST 2020
     */
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column collection.question_id
     *
     * @param questionId the value for collection.question_id
     *
     * @mbg.generated Tue Jun 30 23:42:30 CST 2020
     */
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column collection.folder_id
     *
     * @return the value of collection.folder_id
     *
     * @mbg.generated Tue Jun 30 23:42:30 CST 2020
     */
    public Long getFolderId() {
        return folderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column collection.folder_id
     *
     * @param folderId the value for collection.folder_id
     *
     * @mbg.generated Tue Jun 30 23:42:30 CST 2020
     */
    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column collection.gmt_create
     *
     * @return the value of collection.gmt_create
     *
     * @mbg.generated Tue Jun 30 23:42:30 CST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column collection.gmt_create
     *
     * @param gmtCreate the value for collection.gmt_create
     *
     * @mbg.generated Tue Jun 30 23:42:30 CST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}