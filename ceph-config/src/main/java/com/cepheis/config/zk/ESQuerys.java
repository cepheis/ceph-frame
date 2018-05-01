package com.cepheis.config.zk;//package com.tiangou.order.framework.query;
//
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.query.RangeQueryBuilder;
//
//import java.util.function.BiFunction;
//import java.util.function.Function;
//
//public class ESQuerys {
//
//    public final static BiFunction<String, String, QueryBuilder> match = QueryBuilders::matchQuery;
//
//    public final static BiFunction<String, String, QueryBuilder> prefix = QueryBuilders::prefixQuery;
//
//    public final static BiFunction<String, Object, QueryBuilder> term = QueryBuilders::termQuery;
//    public final static BiFunction<String, Object[], QueryBuilder> terms = QueryBuilders::termsQuery;
//
//    public final static Function<String, RangeQueryBuilder> range = QueryBuilders::rangeQuery;
//
//    public final static BiFunction<String, Object, RangeQueryBuilder> gt = (field, value) -> range.apply(field).gt(value);
//    public final static BiFunction<RangeQueryBuilder, Object, RangeQueryBuilder> innergt = (inner, value) -> inner.gt(value);
//    public final static BiFunction<String, Object, RangeQueryBuilder> gte = (field, value) -> range.apply(field).gte(value);
//    public final static BiFunction<RangeQueryBuilder, Object, RangeQueryBuilder> innergte = (inner, value) -> inner.gte(value);
//    public final static BiFunction<String, Object, RangeQueryBuilder> lt = (field, value) -> range.apply(field).lt(value);
//    public final static BiFunction<RangeQueryBuilder, Object, RangeQueryBuilder> innserlt = (inner, value) -> inner.lt(value);
//    public final static BiFunction<String, Object, RangeQueryBuilder> lte = (field, value) -> range.apply(field).lte(value);
//    public final static BiFunction<RangeQueryBuilder, Object, RangeQueryBuilder> innerlte = (inner, value) -> inner.lte(value);
//
////    public <V> void add(String field, BiFunction<String, V, RangeQueryBuilder> opt) {
////        BiFunction<BiFunction<String, Object, RangeQueryBuilder>, BiFunction<String, V, RangeQueryBuilder>, RangeQueryBuilder> f =
////                (f1, f2) -> f2.andThen(builder-> )
////    }
//
//    public static <V> QueryBuilder gen(String fieldname, V value, BiFunction<String, V, QueryBuilder> function) {
//        return function.apply(fieldname, value);
//    }
//
//    public static void main(String[] args) {
////        gen("id", new Object(), term);
////        term.apply("id", new Object());
////        IntStream.range(0, 5).forEach(e -> {
////            System.out.println( + "====");
////        });
////        Integer[] integers = new Integer[]{1};
////        System.out.println(gen("id", integers, terms) + "====");
////        System.out.println(gt.apply("id", 100) + "====");
////        System.out.println(gt.apply("id", 100) + "====");
////        System.out.println(gt.apply("id", 100) + "====");
//    }
//}
