package cn.com.basic.java8.Lambda;

import java.util.function.Supplier;

/**
 * Created by ZhaiJiaYi on 2016/8/12.
 */
public class Lambda {

    public static void main(String[] agrs) {
        //previous
        Converter<String> converter1 = new Converter<String>(){
            @Override
            public void convert(String from) {
               System.out.println("conver1 : "+from);
            }
        };
        converter1.convert("200");

        //New: Lambda
        Converter<String> converter2 = (s) -> System.out.println("conver2 : "+s);
        converter2.convert("0123");

        //previous
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello lambda");
            }
        }).start();

        //New: Lambda
        new Thread(()->{System.out.print("hello lambda");}).start();
        System.out.println( );

        //接口默认方法和静态方法
        Defaulable defaulable = DefaulableFactory.create( DefaultableImpl::new );
        System.out.println( defaulable.notRequired() );

        defaulable = DefaulableFactory.create( OverridableImpl::new );
        System.out.println( defaulable.notRequired() );
    }
    private interface Defaulable {
        // Interfaces now allow default methods, the implementer may or
        // may not implement (override) them.
        default String notRequired() {
            return "Default implementation";
        }
    }
    private interface DefaulableFactory {
        // Interfaces now allow static methods
        static Defaulable create( Supplier< Defaulable > supplier ) {
            return supplier.get();
        }
    }

    private static class DefaultableImpl implements Defaulable {
    }

    private static class OverridableImpl implements Defaulable {
        @Override
        public String notRequired() {
            return "Overridden implementation";
        }
    }

    private static class OverridConverterImpl implements Converter {
        @Override
        public void convert(Object from) {
            System.out.println("OverridConverterImpl");
        }

        @Override
        public String notRequired() {
            return "Overridden implementation:default";
        }
    }

}
