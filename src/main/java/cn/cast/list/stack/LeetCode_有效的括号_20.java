package cn.cast.list.stack;

import java.util.HashMap;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/20 16:37
 */
public class LeetCode_有效的括号_20 {

    public static HashMap<Character,Character> map = new HashMap<>();

    static {
        map.put('(',')');
        map.put('[',']');
        map.put('{','}');
    }

    public boolean isValid(String s)
    {
        int len = s.length();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{')
            {
                stack.push(c);
            }
            else
            {
                if (stack.isEmpty()) return false;
                Character pop = stack.pop();
                if (pop == '(' && c != ')') return false;
                if (pop == '[' && c != ']') return false;
                if (pop == '{' && c != '}') return false;
            }
        }
        return stack.isEmpty();
    }

    public boolean isValid2(String s)
    {
        int len = s.length();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (map.containsKey(c))
            {
                stack.push(c);
            }
            else
            {
                if (stack.isEmpty()) return false;
                if (c != map.get(stack.pop())) return false;
            }
        }
        return stack.isEmpty();
    }
}
