package com.example.senthurpandi.myapplication;

/**
 * Created by Senthurpandi on 19-Nov-16.
 */

public class Contact {

    String email, name, pass, pno, time, answer, qno;
    byte[] imageBytes;
    int id;

    public void setId(int id)
    {
        this.id=id;
    }
    public int getId()
    {
    return this.id;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getPno() {
        return this.pno;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    public void setQno(String qno) {
        this.qno = qno;
    }

    public String getQno() {
        return this.qno;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setImage(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public byte[] getImage() {
        return this.imageBytes;

    }
}
