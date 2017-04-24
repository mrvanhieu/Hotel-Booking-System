package com.edu.mum.hbs.dao;

import java.util.List;

import com.edu.mum.hbs.entity.User;
import com.edu.mum.hbs.util.SqliteUtil;

public class LoginDao extends DaoAbstract<User,String> {
    public LoginDao() {
		super(User.class);
		// TODO Auto-generated constructor stub
	}

	private static final String TABLE_NAME = "user";
    UserSession session = null;
    
    
    public UserSession validateLogin(String username, String password) {
        // check with database whether user is logged in or not
    	
        SqliteUtil.FilterCondition condition = new SqliteUtil.FilterCondition(SqliteUtil.LogicalOperator.AND);
        condition.addCondition("username", SqliteUtil.EQUALS, username);
        condition.addCondition("password", SqliteUtil.EQUALS, password);
        List<User> users = getAll(condition);

        // initialize session
        if(users.size() > 0) {
            int user_id = (int)(users.get(0).getUser_id());
            session = new UserSession();
            session.setMemberId(user_id);
            session.setUsername(username);
        }
        return session;
    }

}

