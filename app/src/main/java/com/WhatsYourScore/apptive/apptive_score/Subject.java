package com.WhatsYourScore.apptive.apptive_score;

/**
 * Created by apptive on 2016-11-15.
 */
//subject완성
public class Subject {
  private String subject;
  private String grade;
  private int button;

  public Subject(String par_subject) {
    subject = par_subject;

  }

  public Subject(String par_subject, String par_grade) {
    subject = par_subject;
    grade = par_grade;
  }

  public Subject(String par_subject, String par_grade, int par_button) {
    subject = par_subject;
    grade = par_grade;
    button = par_button;
  }

  public String getSubject() {
    return this.subject;
  }

  public String getGrade() {
    return this.grade;
  }

  public int getButton() {
    return this.button;
  }

}
