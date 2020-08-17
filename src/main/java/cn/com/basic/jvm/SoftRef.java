package cn.com.basic.jvm;

import java.lang.ref.SoftReference;

public class SoftRef {
    public static class User {
        private int id;
        private String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        User user = new User(1,"tom");
        SoftReference<User> userSoftRef = new SoftReference<User>(user);

        user = null;

        System.out.println(userSoftRef.get());
        System.gc();

        System.out.println("after gc   : ");
        System.out.println(userSoftRef.get());

        System.out.println("maxMemory   : "+     Runtime.getRuntime().maxMemory());

        System.out.println("release    : "+1024*925*1000);
        byte[] bytes = new byte[1024*925*1000];
        System.gc();

        System.out.println(userSoftRef.get());


    }

}
