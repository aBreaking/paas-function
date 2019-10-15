package com.sitech.paas.parse;

import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

/**
 *
 * @author liwei_paas
 * @date 2019/10/14
 */
public class HtmlFinder {

    private Html html;

    public HtmlFinder(Html html){
        this.html = html;
    }

    public static String findText(Selectable selectable){
        return selectable.xpath("allText()").get();
    }

    /**
     * 直接根据指定的选择器找到该元素下面的value
     * @param selector
     * @return 文本值。 null if no find
     */
    public String findText(String selector){
        return findSelectable(selector).xpath("allText()").get();
    }

    public String findLinks(String selector){
        return findSelectable(selector).links().get();
    }

    /**
     * 到某个选择器下面
     * @param selector
     * @return
     */
    public Selectable findSelectable(String selector){
        return html.$(selector);
    }
}
