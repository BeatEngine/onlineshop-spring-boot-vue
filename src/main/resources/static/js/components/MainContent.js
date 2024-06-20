import { ref } from '../vue/vue.esm-browser.prod.min.js'
import addCSS from '../AppUtils.js'

import { GlobalState } from '../GlobalState.js'

import Articles from './MainContent/Articles.js'
import MyArticles from './MainContent/MyArticles.js'
import SoldArticles from './MainContent/SoldArticles.js'
import MyOrders from './MainContent/MyOrders.js'
import MyCart from './MainContent/MyCart.js'
import CurrentOrder from './MainContent/CurrentOrder.js'

export default {
    name: 'MainContent',
    components: {
          Articles,
          MyArticles,
          SoldArticles,
          MyOrders,
          MyCart,
          CurrentOrder
    },
    props: {
    }, /*HERE are all views of the SPA defined loaded when the property currentPage changes*/
    template: `
        <div class="main-content">
            <Articles v-if="globalState.currentPage == globalState.pagesDefinition.ARTICLES" />
            <MyArticles v-if="globalState.currentPage == globalState.pagesDefinition.MY_ARTICLES" />
            <SoldArticles v-if="globalState.currentPage == globalState.pagesDefinition.SOLD_ARTICLES" />
            <MyOrders v-if="globalState.currentPage == globalState.pagesDefinition.MY_ORDERS" />
            <MyCart v-if="globalState.currentPage == globalState.pagesDefinition.MY_CART" />
            <CurrentOrder v-if="globalState.currentPage == globalState.pagesDefinition.CURRENT_ORDER" />
        </div>
    `,
    setup(props) {
        addCSS('./css/components/MainContent.css'); /* Add the style-link for this component to the head */
        const globalState = ref(GlobalState);
        return { globalState };
    }
};