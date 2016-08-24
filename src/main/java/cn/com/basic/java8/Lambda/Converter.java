package cn.com.basic.java8.Lambda;

import java.util.function.Supplier;

/**
 * Created by ZhaiJiaYi on 2016/8/12.
 */
@FunctionalInterface
interface Converter< T>{

    void convert(T from);

    default String notRequired() {
        return "Default implementation";
    }

    public static Converter create( final Supplier< Converter > supplier ) {
        return supplier.get();
    }


}
