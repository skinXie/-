package com.social.articleservice.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SensetiveService implements InitializingBean {
    class trieNode {
        private Map<Character, trieNode> map = new HashMap<>();
        private boolean isEnd = false;

        public trieNode getSub(Character c) {
            return map.get(c);
        }

        public void setEnd() {
            isEnd = true;
        }
    }

    private trieNode rootNode = new trieNode();
    //添加敏感词
    public void addNode(String content) {
        trieNode cur = rootNode;
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            trieNode node = cur.getSub(c);
            if (node == null)
                cur.map.put(c, new trieNode());
            else
                cur = node;
            if (i == content.length() - 1)
                cur.map.get(c).setEnd();
        }
    }
    //文本过滤
    public String filter(String content) {
        StringBuilder result = new StringBuilder();
        int begin = 0, cur = 0;
        trieNode curNode = rootNode;
        while (cur < content.length()) {
            char c = content.charAt(cur);
            if ((curNode = curNode.map.get(c)) == null) {
                result.append(content.charAt(begin));
                cur = ++begin;
                curNode = rootNode;
            } else {
                if (curNode.isEnd) {
                    result.append("***");
                    begin = ++cur;
                    curNode = rootNode;
                } else
                    cur++;
            }
        }
        result.append(content.charAt(begin));
        return result.toString();
    }

    //读取文件
    public void afterPropertiesSet() throws Exception {

    }
}
