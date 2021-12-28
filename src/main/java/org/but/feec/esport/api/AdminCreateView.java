package org.but.feec.esport.api;

import java.util.Arrays;

public class AdminCreateView {
    private String email;
    private String given_name;
    private String nickname;
    private String family_name;
    private char[] pwd;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public char[] getPwd() {
        return pwd;
    }

    public void setPwd(char[] pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "PersonCreateView{" +
                "email='" + email + '\'' +
                ", given_name='" + given_name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", family_name='" + family_name + '\'' +
                ", pwd=" + Arrays.toString(pwd) +
                '}';
    }
}
