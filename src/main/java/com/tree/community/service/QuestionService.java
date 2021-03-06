package com.tree.community.service;

import com.tree.community.dto.PaginationDTO;
import com.tree.community.dto.QuestionDTO;
import com.tree.community.dto.QuestionQueryDTO;
import com.tree.community.enums.QuestionTypeEnum;
import com.tree.community.exception.CustomizeErrorCode;
import com.tree.community.exception.CustomizeException;
import com.tree.community.mapper.*;
import com.tree.community.model.*;
import com.tree.community.util.ScoreToGradeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CollectionExtMapper collectionExtMapper;

    @Autowired
    private UserExtMapper userExtMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private UserLikeMapper userLikeMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private UserLikeService userLikeService;

    public PaginationDTO list(String search, String tag, Integer page, Integer size,String type,String sort,List<Long> quIds) {
        if(StringUtils.isNotBlank(search)){
            String[] tags = StringUtils.split(search, " ");
            search = Arrays
                    .stream(tags)
                    .filter(StringUtils::isNotBlank)
                    .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining("|"));
        }

        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        if (StringUtils.isNotBlank(tag)) {
            tag = tag.replace("+", "").replace("*", "").replace("?", "");
            questionQueryDTO.setTag(tag);
        }
        questionQueryDTO.setType(type);
        questionQueryDTO.setQuIds(quIds);
        if(StringUtils.isNotBlank(sort)){
            if(sort.equals("hot7")){
                questionQueryDTO.setSort(7);
            }else if(sort.equals("hot30")){
                questionQueryDTO.setSort(32);
            }else if(sort.equals("no")){
                questionQueryDTO.setSort(0);
            }else if(sort.equals("good")){
                questionQueryDTO.setSort(2);
            }
        }
        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);

        if(totalCount % size ==0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }

        if(page < 1){
            page = 1;
        }
        if(page>totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        //分页
        Integer offset = page < 1 ? 0 : size*(page-1);
        questionQueryDTO.setPage(offset);
        questionQueryDTO.setSize(size);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            user.setGrade(ScoreToGradeUtils.scoreToGrade(user.getScore()));
            questionDTO.setUser(user);
            questionDTO.setTypeName(QuestionTypeEnum.nameOfType(question.getType()));
            Integer collectionCount = collectionExtMapper.getCollectionCount(question.getId());
            questionDTO.setCollectionCount(collectionCount);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(example);
        paginationDTO.setQuCount(totalCount);

        if(totalCount % size ==0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }

        if(page < 1){
            page = 1;
        }
        if(page>totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        //分页
        Integer offset = page < 1 ? 0 : size*(page-1);
        QuestionExample example1 = new QuestionExample();
        example1.createCriteria()
                .andCreatorEqualTo(userId);
        example1.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example1,new RowBounds(offset,size));

        if(questions.size() == 0){
            return paginationDTO;
        }

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTO.setTypeName(QuestionTypeEnum.nameOfType(question.getType()));
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setTypeName(QuestionTypeEnum.nameOfType(question.getType()));
        user.setGrade(ScoreToGradeUtils.scoreToGrade(user.getScore()));
        questionDTO.setUser(user);
        Integer collectionCount = collectionExtMapper.getCollectionCount(id);
        questionDTO.setCollectionCount(collectionCount);
        return questionDTO;
    }

    @Transactional
    public void createOrUpdate(Question question, HttpServletRequest request) {
        if(question.getId() == null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.insertSelective(question);
            userExtMapper.addScoreOfQu(question.getCreator());
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            request.getSession().setAttribute("user",user);
        }else{
            //更新
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(question, example);
            if(updated != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    @Transactional
    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<Question> selectRelated(QuestionDTO questionDTO) {
        if(StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionDTO.getTag(), ",");
        String regexpTag = Arrays
                .stream(tags)
                .filter(StringUtils::isNotBlank)
                .map(t -> t.replace("+", "").replace("*", ""))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        return questions;
    }

    public int getQuCountById(Long id) {
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(id);
        int quCount = (int) questionMapper.countByExample(example);
        return quCount;
    }

    public List<Question> getQuestionByIds(List<Long> questionIds) {
        List<Question> questions = questionExtMapper.getQuestionByIds(questionIds);
        return questions;
    }

    @Transactional
    public void deleteQu(Long questionId,Long authorId) {
        userLikeService.transLikedFromRedisToDB();
        userLikeService.transLikedCountFromRedisToDB();
        //删除作者所获积分
        List<Long> commentatorIds = commentExtMapper.getCommentatorByQuId(questionId);
        User user = new User();
        user.setId(authorId);
        user.setScore(10 + commentatorIds.size());
        userExtMapper.reduceAuthorScore(user);
        //删除评论
        CommentExample example3 = new CommentExample();
        example3.createCriteria()
                .andQuestionIdEqualTo(questionId);
        commentMapper.deleteByExample(example3);
        //删除帖子
        questionMapper.deleteByPrimaryKey(questionId);
        //删除消息
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andOuteridEqualTo(questionId);
        notificationMapper.deleteByExample(example);
        //删除收藏
        CollectionExample example1 = new CollectionExample();
        example1.createCriteria()
                .andQuestionIdEqualTo(questionId);
        collectionMapper.deleteByExample(example1);
        //删除点赞
        UserLikeExample example2 = new UserLikeExample();
        example2.createCriteria()
                .andQuestionIdEqualTo(questionId);
        userLikeMapper.deleteByExample(example2);
    }

    @Transactional
    public void essence(Question question) {
        questionMapper.updateByPrimaryKeySelective(question);
    }

    @Transactional
    public void quTop(Question question) {
        questionMapper.updateByPrimaryKeySelective(question);
    }

    public List<Question> getQuestion(Integer page, Integer limit) {
        int start = page * limit;
        List<Question> essences = questionExtMapper.getQuestion(start, limit);
        for(Question q : essences){
            System.out.println(q);
        }

        return essences;
    }

    public List<Question> getAllQuestion(Integer page, Integer limit){
        int start = page * limit;
        QuestionExample example = new QuestionExample();
        example.createCriteria().andIdIsNotNull().andIdBetween((long) start, (long)limit);
        return questionMapper.selectByExample(example);
    }

    public List<Question> getQuestionByType(Integer page, Integer limit, Integer type){
        int start = page * limit;
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andIdIsNotNull()
//                .andIdBetween((long) start, (long)limit)
                .andTypeEqualTo(type);
        return questionMapper.selectByExample(example);
    }
    public int getQuestionCount() {
        int count = questionExtMapper.getQuestionCount();
        return count;
    }

    @Transactional
    public void questionEdit(Question question) {
        questionExtMapper.questionEdit(question);
    }

    public Question getQuById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        return question;
    }

    public List<QuestionDTO> questionTop() {
        List<Question> questions = questionExtMapper.questionTop();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            user.setGrade(ScoreToGradeUtils.scoreToGrade(user.getScore()));
            questionDTO.setUser(user);
            questionDTO.setTypeName(QuestionTypeEnum.nameOfType(question.getType()));
            Integer collectionCount = collectionExtMapper.getCollectionCount(question.getId());
            questionDTO.setCollectionCount(collectionCount);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
