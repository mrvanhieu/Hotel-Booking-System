package com.edu.mum.hbs.dao;

import com.edu.mum.hbs.util.SqliteUtil;
import java.util.Map;

public class LoginDao extends DaoAbstract {
    private static final String TABLE_NAME = "user";
    UserSession session = null;
    
    public UserSession validateLogin(String username, String password) {
        // check with database whether user is logged in or not
        SqliteUtil.FilterCondition condition = new SqliteUtil.FilterCondition(SqliteUtil.LogicalOperator.AND);
        condition.addCondition("username", SqliteUtil.EQUALS, username);
        condition.addCondition("password", SqliteUtil.EQUALS, password);
        Map<String, Object> member = db.findOne(TABLE_NAME, new String[] { "user_id" }, condition);

        // initialize session
        if(member != null) {
            int user_id = (int)(member.get("user_id"));
            session = new UserSession();
            session.setMemberId(user_id);
            session.setUsername(username);
        }
        return session;
    }

}

