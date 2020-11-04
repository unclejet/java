package com.uj.study.functional;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/30 下午1:21
 * @description：
 * @modified By：
 * @version:
 */
public class DefaultMethod {
    public interface Parent {

        public void message(String body);

        public default void welcome() {
            message("Parent: Hi!");
        }

        public String getLastMessage();

    }

    public class ParentImpl implements Parent {

        private String body;

        @Override
        public void message(String body) {
            this.body = body;
        }

        @Override
        public String getLastMessage() {
            return body;
        }

    }

    public class OverridingParent extends ParentImpl {

        @Override
        public void welcome() {
            message("Class Parent: Hi!");
        }

    }


    public interface Child extends Parent {

        @Override
        public default void welcome() {
            message("Child: Hi!");
        }

    }

    public class ChildImpl extends ParentImpl implements Child {
    }

    public class OverridingChild extends OverridingParent implements Child {

    }






    //多重继承

    public interface Carriage {

        public default String rock() {
            return "... from side to side";
        }

    }

    public interface Jukebox {

        public default String rock() {
            return "... all over the world!";
        }

    }

    public class MusicalCarriage
            implements Carriage, Jukebox {

        @Override
        public String rock() {
            return Carriage.super.rock();
        }

    }

}
