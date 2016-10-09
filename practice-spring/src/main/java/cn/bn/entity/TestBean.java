package cn.bn.entity;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-10-08 AM11:21
 */
public class TestBean implements InitializingBean {
    private String data;
    private String source;
    private DataBean dataBean;
    @Override
    public void afterPropertiesSet() throws Exception {
        dataBean = new DataBean();
        dataBean.setData(data);
        dataBean.setSource(source);
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "data='" + data + '\'' +
                ", source='" + source + '\'' +
                ", dataBean=" + dataBean.toString() +
                '}';
    }
}
