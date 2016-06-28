package com.ast.ast1949.front.util;

public class TestRandom {
	public String getPassword() {
        int index = 0;
        String[] p_array = new String[62];
        /**
         * 填充数组
         */
        for (int i = 0; i <= 9; i++) {
            p_array[index++] = i + "";
        }

        for (int i = 'A'; i <= 'Z'; i++) {
            char c = (char) i;
            p_array[index++] = new String(c + "");
        }

        for (int i = 'a'; i <= 'z'; i++) {
            char c = (char) i;
            p_array[index++] = new String(c + "");
        }

        /**
         * 返回6位的随机密码
         */
        StringBuffer password = new StringBuffer();

        for (int i = 0; i < 6; i++) {
            int random =(int)(Math.random() * 62);
            password.append(p_array[random]);
        }
        
        boolean rule = this.rule(password.toString());

        if(rule){
            return password.toString();
        }else{
            return this.getPassword();
        }
    }

    private boolean rule(String password) {
        char[] p_array = password.toCharArray();

        int rule =     p_array[0];
        for (int i = 0; i < 6; i++) {
            if(p_array[i] != rule + i){
                return true;
            }
        }
        return false;
    }
}
