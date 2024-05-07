package com.example.project1.constants;

public final class Constants {
    public static final String PHONE_NUMBER_REGEX = "^((\\+\\d{1,3}(-| )?\\(?\\d\\)?(-| )?\\d{1,3})|(\\(?\\d{2,3}\\)?))(-| )?(\\d{3,4})(-| )?(\\d{4})(( x| ext)\\d{1,5}){0,1}$";
    public static final String EMAIL_REGEX = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_]).{6,}$";
    public static final String[] BOOK_HEADER = {"title", "languageId", "categoryId", "publisherId", "authorId", "price", "numOfPages", "description", "quantity", "image", "isDelete"};
    public static String TYPE_CSV = "text/csv";

    public static String PATH_UPLOAD_IMAGE = "src/main/resources/images/books";
    public static final class ERROR_CODE {
        public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
        public static final String INVALID_TOKEN = "INVALID_TOKEN";
        public static final String INVALID_CODE = "INVALID_CODE";
        public static final String SESSION_NOT_FOUND = "SESSION_NOT_FOUND";
        public static final String ACCOUNT_WAS_VERIFY = "ACCOUNT_WAS_VERIFY";
        public static final String INVALID_PASSWORD = "INVALID_PASSWORD";
        public static final String DUPLICATE_OLD_PASSWORD_NEW_PASSWORD = "DUPLICATE_OLD_PASSWORD_NEW_PASSWORD";
        public static final String INVALID_PASSWORD_AND_CONFIRM_PASSWORD = "INVALID_PASSWORD_AND_CONFIRM_PASSWORD";
        public static final String EMAIL_ALREADY_EXISTS = "EMAIL_ALREADY_EXISTS";
        public static final String DUPLICATE_OLD_EMAIL_AND_NEW_EMAIL = "DUPLICATE_OLD_EMAIL_AND_NEW_EMAIL";
        public static final String PHONE_NUMBER_ALREADY_EXISTS = "PHONE_NUMBER_ALREADY_EXISTS";
        public static final String DUPLICATE_OLD_PHONE_AND_NEW_PHONE = "DUPLICATE_OLD_PHONE_AND_NEW_PHONE";
        public static final String INVALID_SESSION = "INVALID_SESSION";
        public static final String BLOCK_ACCOUNT = "BLOCK_ACCOUNT";
        public static final String DONT_HAVE_PERMISSION = "DONT_HAVE_PERMISSION";
        public static final String EXPIRED_CODE = "EXPIRED_CODE";
        public static final String IS_USED_CODE = "IS_USED_CODE";
        public static final String NOT_ACTIVE_ACCOUNT = "NOT_ACTIVE_ACCOUNT";
        public static final String EXPIRED_SESSION = "EXPIRED_SESSION";
        public static final String VERIFY_EMAIL = "VERIFY_EMAIL";
        public static final String REQUIRE_CHANGE_PASSWORD = "REQUIRE_CHANGE_PASSWORD";
        public static final String INVALID_AUTHORITY = "INVALID_AUTHORITY";
        public static final String REFRESH_TOKEN_NOT_FOUND = "REFRESH_TOKEN_NOT_FOUND";
        public static final String EMAIL_NOT_FOUND = "EMAIL_NOT_FOUND";
        public static final String LINK_VERIFICATION_NOT_FOUND = "LINK_VERIFICATION_NOT_FOUND";

    }

}
