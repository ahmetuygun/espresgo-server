package com.example.polls.util;

public interface AppConstants {
    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "30";

    int MAX_PAGE_SIZE = 50;

    int NEW_ORDER = 0;
    int PRAPARING_ORDER = 1;
    int SENT_ORDER = 2;
    int COMPLATED_ORDER = 3;

    String HISTORY_METHOD = "HISTORY_METHOD";
    String NEWORDERS_METHOD = "";

    String ORDER_STATUS = "ORDER_STATUS";

    String LOCATION_STATUS = "LOCATION_STATUS";

    String OPEN = "1";
    String CLOSE = "0";


    public static class ResponseCode{

        public static Integer SUCCESS = 100;
        public static Integer FAIL = 101;
        public static Integer ORDER_CLOSED = 102;
        public static Integer BUSY = 103;
    }

    public static class ResponseCodeMessage{

        public static String SUCCESS = "Siparişiniz Alındı!";
        public static String FAIL = "Sipariş alınamadı. Whatsapp dan göndermeyi deneyin";
        public static String ORDER_CLOSED = "Malesef Şuan Kapalıyız.";
        public static String BUSY = "Maksimum sipariş sayısına ulaştık.";
    }




}
