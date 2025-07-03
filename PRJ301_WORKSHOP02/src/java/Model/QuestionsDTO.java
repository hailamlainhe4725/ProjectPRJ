/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class QuestionsDTO {
    private int question_id;
    private int exam_id;
    private String question_text;
    private String option_a;
    private String option_b;
    private String option_c;
    private String option_d;
    private String correct_option;

    public QuestionsDTO() {
    }

    public QuestionsDTO(int question_id, int exam_id, String question_text, String option_a, String option_b, String option_c, String option_d, String correct_option) {
        this.question_id = question_id;
        this.exam_id = exam_id;
        this.question_text = question_text;
        this.option_a = option_a;
        this.option_b = option_b;
        this.option_c = option_c;
        this.option_d = option_d;
        this.correct_option = correct_option;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getOption_a() {
        return option_a;
    }

    public void setOption_a(String option_a) {
        this.option_a = option_a;
    }

    public String getOption_b() {
        return option_b;
    }

    public void setOption_b(String option_b) {
        this.option_b = option_b;
    }

    public String getOption_c() {
        return option_c;
    }

    public void setOption_c(String option_c) {
        this.option_c = option_c;
    }

    public String getOption_d() {
        return option_d;
    }

    public void setOption_d(String option_d) {
        this.option_d = option_d;
    }

    public String getCorrect_option() {
        return correct_option;
    }

    public void setCorrect_option(String correct_option) {
        this.correct_option = correct_option;
    }

    @Override
    public String toString() {
        return "QuestionsDTO{" + "question_id=" + question_id + ", exam_id=" + exam_id + ", question_text=" + question_text + ", option_a=" + option_a + ", option_b=" + option_b + ", option_c=" + option_c + ", option_d=" + option_d + ", correct_option=" + correct_option + '}';
    }
    
    
}
