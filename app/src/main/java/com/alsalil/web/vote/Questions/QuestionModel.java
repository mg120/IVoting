package com.alsalil.web.vote.Questions;

/**
 * Created by Ma7MouD on 3/28/2018.
 */

public class QuestionModel {

    private int ques_answer_id ;
    private String ques_item_img ;
    private String ques_item_title ;

    public QuestionModel(int ques_answer_id, String ques_item_img, String ques_item_title) {
        this.ques_answer_id = ques_answer_id;
        this.ques_item_img = ques_item_img;
        this.ques_item_title = ques_item_title;
    }

    public int getQues_answer_id() {
        return ques_answer_id;
    }

    public void setQues_answer_id(int ques_answer_id) {
        this.ques_answer_id = ques_answer_id;
    }

    public String getQues_item_img() {
        return ques_item_img;
    }

    public void setQues_item_img(String ques_item_img) {
        this.ques_item_img = ques_item_img;
    }

    public String getQues_item_title() {
        return ques_item_title;
    }

    public void setQues_item_title(String ques_item_title) {
        this.ques_item_title = ques_item_title;
    }
}
