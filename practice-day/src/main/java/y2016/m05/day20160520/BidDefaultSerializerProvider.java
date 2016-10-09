package y2016.m05.day20160520;

import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

/**
 * TODO liudejian 类描述.
 * 
 * @version : Ver 1.0
 * @author : <a href="mailto:dejianliu@ebnew.com">liudejian</a>
 * @date : 2015-10-15 下午4:28:55
 */
public class BidDefaultSerializerProvider extends DefaultSerializerProvider {

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -2891437067035919790L;

	public BidDefaultSerializerProvider() {
		super();
	}

	public BidDefaultSerializerProvider(BidDefaultSerializerProvider src) {
		super(src);
	}

	protected BidDefaultSerializerProvider(SerializerProvider src,
										   SerializationConfig config, SerializerFactory f) {
		super(src, config, f);
	}

	@Override
	public DefaultSerializerProvider copy() {
		if (getClass() != BidDefaultSerializerProvider.class) {
			return super.copy();
		}
		return new BidDefaultSerializerProvider(this);
	}

	@Override
	public BidDefaultSerializerProvider createInstance(
			SerializationConfig config, SerializerFactory jsf) {
		return new BidDefaultSerializerProvider(this, config, jsf);
	}
}
