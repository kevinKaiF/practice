package y2016.m05.day20160520;

import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.introspect.BasicBeanDescription;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
//import org.codehaus.jackson.map.SerializationConfig;
//import org.codehaus.jackson.map.introspect.BasicBeanDescription;
//import org.codehaus.jackson.map.ser.BeanSerializerFactory;

/**
 * @description:重写jackson 工厂类，增加过滤与仅包含
 * @version  Ver 1.0
 * @author   <a href="mailto:zuiwoxing@gmail.com">刘德建</a>
 * @Date	 2013-3-22 上午9:20:04
 */
public class BidBeanSerializerFactory extends BeanSerializerFactory {

    /**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 8778869910011683822L;

	public final static BidBeanSerializerFactory instance = new BidBeanSerializerFactory(null);
    
    private Object filterId;
    
	protected BidBeanSerializerFactory(SerializerFactoryConfig config) {
		super(config);
	}
	
    protected synchronized Object findFilterId(SerializationConfig config,
    		BasicBeanDescription beanDesc) {
    	return getFilterId();
    }

	public Object getFilterId() {
		return filterId;
	}

	public void setFilterId(Object filterId) {
		this.filterId = filterId;
	}
}
