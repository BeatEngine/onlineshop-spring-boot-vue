import { reactive } from './vue/vue.esm-browser.prod.min.js'

export const GlobalState = reactive({
    /* Everything from Server */
    accountDetails: {
        userName: '',
        userRoles: [],
    },
    isPurchaser() { /*True if this user has the role for purchasing*/
        return this.accountDetails.userRoles.includes("purchaser");
    } ,
    isAdmin() { /*True if this user has the role for admin*/
        return this.accountDetails.userRoles.includes("admin");
    },
    pagesDefinition: {
        ARTICLES: 'articles',
        MY_ARTICLES: 'my-articles',
        SOLD_ARTICLES: 'sold-articles',
        MY_ORDERS: 'my-orders',
        MY_CART: 'my-cart',
        CURRENT_ORDER: 'current-order'
    },
    currentPage: 'articles',
    loadAccountDetails() {
        /* Here we load all global account information from the REST-API */



    },
    loadPage(page) {
        console.log('Load page ' + page);
        this.currentPage = page;
    }


});