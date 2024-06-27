import { reactive } from './vue/vue.esm-browser.prod.min.js'

export const GlobalState = reactive({
    /* Everything from Server */
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
    accountDetails: {
        auth: '',
        userId: 0,
        email: '',
        displayName: '',
        userRoles: [],
        pictureId: 0
    },
    loadedAccountDetails: false,
    async loadAccountDetails() {
        /* Here we load all global account information from the REST-API */
        try {
            const response = await fetch("./api/account/details");
            const details = await response.json();
            this.accountDetails.auth = details.auth;
            this.accountDetails.userId = details.userId;
            this.accountDetails.email = details.email;
            this.accountDetails.displayName = details.displayName;
            this.accountDetails.userRoles = details.userRoles;
            this.accountDetails.pictureId = details.pictureId;
            this.loadedAccountDetails = true;
        } catch (error) {
            console.error(`loadAccountDetails() fetch failed: ${error}`);
        }
    },
    loadPage(page) {
        console.log('Load page ' + page);
        this.currentPage = page;
    }


});