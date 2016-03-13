package com.example;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
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

public class ParseHtmlTest {

    public static void main(String[] args) {

        try {
            parseMovieHeaven();
//            parseDownloadUrl();
        } catch (ParserException e) {
            e.printStackTrace();
            System.out.println("parse error");
        }
    }

    private static List<String> parseDownloadUrl() throws ParserException {

        List<String> urls = new ArrayList<>();
        final String MV_DOWNLOAD_PAGE_URL = "http://www.dytt8.net/html/gndy/dyzz/20160214/50231.html";

        Parser parser = new Parser(MV_DOWNLOAD_PAGE_URL);
        parser.setEncoding("GB2312");

        // 自定义过滤器
        NodeFilter linkFilter = new NodeFilter() {
            @Override
            public boolean accept(Node node) {
//                return node.getText().startsWith("a href=\"ftp://")
//                        || node.getText().startsWith("title");
                return node.getText().startsWith("img");
            }
        };

        NodeList nodes = parser.extractAllNodesThatMatch(linkFilter);
        System.out.println("nodes.size() = " + nodes.size());

        // 过滤影片名称
//        Node node = nodes.elementAt(0);
//        Node child;
//        if (node != null) {
//            child = node.getFirstChild();
//            if (child != null) {
//                String titleString = child.getText();
//                int start = titleString.indexOf("《");
//                int end = titleString.indexOf("》");
//                String title = titleString.substring(start + 1, end);
//                System.out.println(" title: " + title);
//            }
//        }

        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.elementAt(i);
            if (node != null) {
                String text = node.getText();
                System.out.println("text : " + text);

                Node child = node.getFirstChild();
                if (child != null) {
                    System.out.println("child : " + child.getText());
                    urls.add(child.getText());
                }
            }
        }

        return urls;
    }

    private static List<String> parseMovieHeaven() throws ParserException {

        final String MV_HOME_PAGE_URL = "http://www.dytt8.net";
        ArrayList<String> pTitleList = new ArrayList<>();

        // 创建 html parser 对象，并指定要访问网页的 URL 和编码格式
        Parser parser = new Parser(MV_HOME_PAGE_URL);
        parser.setEncoding("UTF-8");

       // 自定义过滤器
        NodeFilter movieLink = new NodeFilter() {
            @Override
            public boolean accept(Node node) {
                return node.getText().startsWith("a href=\'/html");
            }
        };

        // 使用过滤器过滤
        NodeList nodes = parser.extractAllNodesThatMatch(movieLink);
//        System.out.println("nodes.size() = " + nodes.size());

        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.elementAt(i);

            String text = node.getText();
            int firstIndex = text.indexOf("'");
            int lastIndex = text.lastIndexOf("'");
            String url = text.substring(firstIndex + 1, lastIndex);
            Node child = node.getFirstChild();
            if (child != null) {
//                System.out.println(MV_HOME_PAGE_URL + url + " : " + child.getText());

                String txt = child.getText();
//                System.out.println(" txt : " + txt);
                if (txt.matches("^201.年.+")) {
                    System.out.println(txt + " match ");
                }
            }
        }

        // 过滤影片名称
        NodeFilter titleFilter = node -> {
            return node.getText().startsWith("title");
        };

        NodeList titleNode = parser.extractAllNodesThatMatch(titleFilter);
        Node node = titleNode.elementAt(0);
        if (node != null) {
            Node child = node.getFirstChild();
            if (child != null) {
                String titleString = child.getText();
                int start = titleString.indexOf("《");
                int end = titleString.indexOf("》");
                String title = titleString.substring(start + 1, end);
//                System.out.println(" title: " + title);
            }
        }
        return pTitleList;
    }

    private static List<String> parserDwPost() throws ParserException {

        final String DW_HOME_PAGE_URL = "http://www.ibm.com/developerworks/cn";
        ArrayList<String> pTitleList = new ArrayList<String>();
        // 创建 html parser 对象，并指定要访问网页的 URL 和编码格式
        Parser htmlParser = new Parser(DW_HOME_PAGE_URL);
        htmlParser.setEncoding("UTF-8");
        String postTitle = "";

        // 获取指定的 div 节点，即 <div> 标签，并且该标签包含有属性 id 值为“tab1”
        NodeList divOfTab1 = htmlParser.extractAllNodesThatMatch(
                new AndFilter(
                        new TagNameFilter("div"),
                        new HasAttributeFilter("id", "dw-masthead-top-row")));

        System.out.println("divOfTab1.size() = " + divOfTab1.size());

        if (divOfTab1.size() > 0) {
            // 获取指定 div 标签的子节点中的 <li> 节点
            NodeList itemLiList = divOfTab1.elementAt(0).getChildren()
                    .extractAllNodesThatMatch(new TagNameFilter("li"), true);

            System.out.println("itemLiList.size() = " + itemLiList.size());

            if (itemLiList.size() > 0) {
                for (int i = 0; i < itemLiList.size(); ++i) {
                    // 在 <li> 节点的子节点中获取 Link 节点
                    NodeList linkItem = itemLiList
                            .elementAt(i)
                            .getChildren()
                            .extractAllNodesThatMatch(
                                    new NodeClassFilter(LinkTag.class), true);

                    System.out.println("linkItem.size() = " + linkItem.size());

                    if (linkItem.size() > 0) {
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
