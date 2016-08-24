package cn.com.basic.java8.Lambda;

import java.util.function.Supplier;

/**
 * Created by ZhaiJiaYi on 2016/8/17.
 */
public interface ConverterFactory {
    static Converter create( Supplier< Converter> supplier ) {
        return supplier.get();
    }
}
