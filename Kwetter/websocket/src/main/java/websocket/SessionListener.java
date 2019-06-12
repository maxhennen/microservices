package websocket;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionListener {

    private static SessionListener sessionListener = null;
    private static List<String> activeUsers;
    private static Map<String, Session> sessionMap;

    protected SessionListener() {
        activeUsers = new ArrayList<>();
        sessionMap = new HashMap<>();
    }

    public static SessionListener getInstance(){
        if(sessionListener == null){
            sessionListener = new SessionListener();
        }
        return sessionListener;
    }

    public static List<String> getActiveUsers() {
        return activeUsers;
    }

    public static Map<String, Session> getSessionMap() {
        return sessionMap;
    }
}
