package com.emiancang.emiancang.user;

import com.litesuits.common.assist.Check;
import com.emiancang.emiancang.App;
import com.emiancang.emiancang.uitl.JsonUtil;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.zwyl.sql.LiteSql;

import java.util.ArrayList;

public enum UserManager {

    INSTANCE;

    public static UserManager getInstance() {
        return INSTANCE;
    }

    private User mUser;

    private LiteSql liteSql;


    private UserManager() {
        liteSql = new LiteSql(App.getContext());
        ArrayList<User> list = liteSql.query(User.class);
        if (!Check.isEmpty(list)) {
            mUser = list.get(0);
        }
    }

    public String getToken() {

        if (mUser != null) {
            return mUser.getToken();
        } else {
            return "";
        }
    }

    public boolean isLogin() {
        return mUser != null;
    }

    public void add(User user) {
        liteSql.deleteAll(User.class);
        liteSql.update(user);
        mUser = user;
        SharedPrefsUtil.putValue(App.getContext(),SharedPrefsUtil.USERKEY, JsonUtil.toJson(user));
    }


    public void remove() {
        if (mUser != null) {
            liteSql.delete(mUser);
        }
        mUser = null;
        SharedPrefsUtil.putValue(App.getContext(),SharedPrefsUtil.USERKEY, "");
    }

    public User getUser() {
        return mUser;
    }

    public String getUserID() {
        if (isLogin())
            return mUser.getESjzcNm();
        else
            return null;
    }
    public String getUserCategory() {
        if (isLogin())
            return mUser.getESjzcHylb();
        else
            return null;
    }
}
