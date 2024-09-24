package tot.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MemberUtil {
	
	private static final Map<String, String> userCredentials = new HashMap<>();
    private static String loggedInUser = null;

    static {
        // 테스트용 사용자 정보 추가
        userCredentials.put("user1", "password1");
    }

    // 로그인 시도 함수
    public static boolean login(String username, String password) {
        if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
            loggedInUser = username;
            return true;
        }
        return false;
    }

    // 로그아웃 함수
    public static void logout() {
        loggedInUser = null;
    }

    // 로그인 상태 확인 함수
    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }

    // 현재 로그인 사용자 정보 반환
    public static String getLoggedInUser() {
        return loggedInUser;
    }

    public static void main(String[] args) {
        // 테스트용
        System.out.println("로그인 시도...");
        boolean loginResult = login("user1", "password1");
        System.out.println("로그인 성공: " + loginResult);
        System.out.println("로그인 상태: " + (isLoggedIn() ? "로그인됨" : "로그인 안됨"));
        System.out.println("현재 로그인 사용자: " + getLoggedInUser());

        // 로그아웃 테스트
        logout();
        System.out.println("로그아웃 후 상태: " + (isLoggedIn() ? "로그인됨" : "로그인 안됨"));
    }
	
}
