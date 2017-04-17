package com.edu.mum.hbs.dao;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class UserSession {

    private int memberId;
    private String username;
    private List<String> role;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public List<String> getRole() {
        return role;
    }


    public void setRole(List<String> role) {
        this.role = role;
    }


    public boolean isLoggedIn() {

        if (this.getMemberId() != 0 && this.getUsername() != null && this.getRole() != null)
            return true;
        else
            return false;
    }

    public boolean isAdmin() {
        System.out.println(this.getRole());
        if (this.getRole().contains("Administrator"))
            return true;
        else
            return false;
    }

    public static class GetSession {
        private static UserSession userSession;

        public static void setSession(UserSession session) {
            userSession = session;
        }

        public static UserSession getSession() {
            return userSession;
        }
    }

}

