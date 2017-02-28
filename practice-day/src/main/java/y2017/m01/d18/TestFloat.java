package y2017.m01.d18;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2017-01-18 AM09:44
 */
public class TestFloat {
    @Test
    public void testFloat() {
        System.out.println(Float.floatToIntBits(4.2F));
        System.out.println(Float.floatToRawIntBits(4.29999999F));
        /**
         * 31位代表正负，
         * 30-23代表指数 10000001
         * 20-0代表浮点数的位数 00001100110011001100110
         */
        System.out.println(Integer.toBinaryString(Float.floatToIntBits(4.2F)));
        System.out.println(Float.intBitsToFloat(1082759578));
    }

    @Test
    public void testJson() {
        List<Map> reviewIndices = new ArrayList<Map>() {
            {
                add(new HashMap() {
                    {
                        put("openingBidPrice", new HashMap<String, Object>() {
                            {
                                put("name", "开标价格(万元)");
                                put("type", 1);
                            }
                        });
                    }
                });

                add(new HashMap() {
                    {
                        put("artimeticalCorrection", new HashMap<String, Object>() {
                            {
                                put("name", "算术修正值");
                                put("type", 4);
                            }
                        });
                    }
                });
                add(new HashMap() {
                    {
                        put("artimeticalCorrectionPrice", new HashMap<String, Object>() {
                            {
                                put("name", "算术修正后的投标价格");
                                put("type", 1);
                            }
                        });
                    }
                });
                add(new HashMap() {
                    {
                        put("bidDeclaration", new HashMap<String, Object>() {
                            {
                                put("name", "投标声明(折扣等)");
                                put("type", 3);
                            }
                        });
                    }
                });
                add(new HashMap() {
                    {
                        put("bidPrice", new HashMap<String, Object>() {
                            {
                                put("name", "投标报价");
                                put("type", 1);
                            }
                        });
                    }
                });
                add(new HashMap() {
                    {
                        put("businessOffset", new HashMap<String, Object>() {
                            {
                                put("name", "商务偏离");
                                put("type", 3);
                            }
                        });
                    }
                });
                add(new HashMap() {
                    {
                        put("technologyOffset", new HashMap<String, Object>() {
                            {
                                put("name", "技术偏离");
                                put("type", 3);
                            }
                        });
                    }
                });
                add(new HashMap() {
                    {
                        put("otherOffset", new HashMap<String, Object>() {
                            {
                                put("name", "其他偏离");
                                put("type", 3);
                            }
                        });
                    }
                });
                add(new HashMap() {
                    {
                        put("offsetPrice", new HashMap<String, Object>() {
                            {
                                put("name", "投标报价(偏离调整后)");
                                put("type", 1);
                            }
                        });
                    }
                });
                add(new HashMap() {
                    {
                        put("otherAdjustment", new HashMap<String, Object>() {
                            {
                                put("name", "其他调整");
                                put("type", 2);
                            }
                        });
                    }
                });
            }
        };

        System.out.println(reviewIndices);
    }
}
