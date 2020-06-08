package com.example.polls.util;

public interface AppConstants {
    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "30";

    int MAX_PAGE_SIZE = 50;
    String TOKEN = "TOKEN";
    String USED = "USED";

    String ILKKAHVE = "ESPRESGO101";




    int NEW_ORDER = 0;
    int PRAPARING_ORDER = 1;
    int SENT_ORDER = 2;
    int COMPLATED_ORDER = 3;

    String HISTORY_METHOD = "HISTORY_METHOD";
    String NEWORDERS_METHOD = "";
    String BY_UID = "BY_UID";

    String ORDER_STATUS = "ORDER_STATUS";

    String WHATSAPP_NUMBER = "WHATSAPP_NUMBER";


    String LOCATION_STATUS = "LOCATION_STATUS";

    String OPEN = "1";
    String CLOSE = "0";




    public static class UserStatus{

        public static Integer ACTIVE = 2;
        public static Integer PASSIVE = 3;
        public static Integer DISABLED = 4;
    }


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
