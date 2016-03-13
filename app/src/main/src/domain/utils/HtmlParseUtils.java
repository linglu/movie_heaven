package com.linky.heaven.domain.utils;

import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linky on 16-2-3.
 *
 */
public class HtmlParseUtils {

    public static List<String> parse() throws ParserException {

        final String DW_HOME_PAGE_URL = "http://www.ibm.com/developerworks/cn";
        ArrayList<String> pTitleList = new ArrayList<>();

        // 创建 html parser 对象，并指定要访问网页的 URL 和编码格式
        Parser htmlParser = new Parser(DW_HOME_PAGE_URL);
        htmlParser.setEncoding("UTF-8");
        String postTitle = "";
        // 获取指定的 div 节点，即 <div> 标签，并且该标签包含有属性 id 值为“tab1”
        NodeList divOfTab1 = htmlParser.extractAllNodesThatMatch(
                new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "tab1")));

        if (divOfTab1 != null && divOfTab1.size() > 0) {

            // 获取指定 div 标签的子节点中的 <li> 节点
            NodeList itemLiList = divOfTab1.elementAt(0).getChildren().extractAllNodesThatMatch
                    (new TagNameFilter("li"), true);

            if (itemLiList != null && itemLiList.size() > 0) {
                for (int i = 0; i < itemLiList.size(); ++i) {
                    // 在 <li> 节点的子节点中获取 Link 节点
                    NodeList linkItem
                            = itemLiList.elementAt(i).getChildren().extractAllNodesThatMatch
                            (new NodeClassFilter(LinkTag.class), true);
                    if (linkItem != null && linkItem.size() > 0) {
                        // 获取 Link 节点的 Text，即为要获取的推荐文章的题目文字
                        postTitle = ((LinkTag) linkItem.elementAt(0)).getLinkText();
                        System.out.println(postTitle);
                        pTitleList.add(postTitle);
                    }
                }
            }
        }
        return pTitleList;

    }
}
