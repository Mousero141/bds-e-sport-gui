package org.but.feec.esport.api;

import java.sql.Timestamp;

public class AdminDetailedView {
        private Long id;
        private String email;
        private String given_name;
        private String nickname;
        private String family_name;
        private Long salary;
        private Timestamp match_time;


        public Timestamp getMatch_time() {
            return match_time;
        }

        public void setMatch_time(Timestamp match_time) {
            this.match_time = match_time;
        }

        public void setSalary(Long salary) {
            this.salary = salary;
        }

        public Long getSalary() {
            return salary;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGiven_name() {
            return given_name;
        }

        public void setGivenName(String given_name) {
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

        @Override
        public String toString() {
            return "AdminEditView{" +
                    "email='" + email + '\'' +
                    ", given_name='" + given_name + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", family_name='" + family_name + '\'' +
                    ", salary='" + salary + '\'' +
                    ", match_time='" + match_time + '\'' +
                    '}';
        }
    }

