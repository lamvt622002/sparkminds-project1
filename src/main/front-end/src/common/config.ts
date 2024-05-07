export const CONFIG = {
    ENDPOINT_API: {
        AUTH:{
            LOGIN: 'api/auth/login',
            REGISTER: 'api/auth/register',
            LOGOUT: 'api/auth/logout',
            REFRESH: 'api/auth/refresh',
            FORGOT_PASSWORD: 'api/auth/forgot-password',
            CHANGE_PASSWORD: 'api/auth/change-password',
            TWO_AUTH:'api/auth/two-auth',
            RESENT_VERIFY_LINK: 'api/auth/resent-verify-link'
        },
        USER:{
            CHANGE_EMAIL:'api/user/change-email',
            CHANGE_PASSWORD:'api/user/change-password',
            CHANGE_PHONE:'api/user/change-phone',
            CHANGE_PHONE_VERIFY:'api/user/change-phone-verify',
            LOGOUT:'api/user/logout'
        },
        BOOK:{
            GET_ALL_BOOK:'api/book/all-book',
            FILTER_BOOK:'api/book/filter',
            SEARCH_BOOK:'api/book/search',
            CREATE_BOOK:'api/book/create-book',
            EDIT_BOOK:'api/book/edit-book/',
            GET_A_BOOK:'api/book/get-book/'
        },
        CATEGORY:{
            GET_ALL_CATEGORY:'api/category/all-category'
        },
        BOOK_LANGUAGE:{
            GET_ALL_BOOK_LANGUAGE:'api/language/all-language'
        },
        PUBLISHER:{
            GET_ALL_PUBLISHER:'api/publisher/all-publisher'
        },
        AUTHOR:{
            GET_ALL_AUTHOR:'api/book-author/all-author'
        }
    },
    PAGE_URLS: {
        LOGIN: '/login',
        LOGOUT: '/logout',
        REGISTER: '/register',
        FORGOT_PASSWORD: '/forgot-password',
        CHANGE_PASSWORD: '/change-password',
        ACCOUNT_SETTING: '/account-setting',
        VERIFY_EMAIL:'/verify-email',
        HOME:'/home'
    },
    IMAGES:{
        EMAIL_VERIFICATION: '/images/email_verifycation_image.jpg',
        SPARKMINDS_LOGO:'/images/logo-sparkminds.png',
        SPARKMINDS_LOGO_MINI:'/images/logo-sparkminds-mini.jpg'
    }
}