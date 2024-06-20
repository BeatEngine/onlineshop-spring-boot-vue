import { ref } from '../../../vue/vue.esm-browser.prod.min.js'
import addCSS from '../../../AppUtils.js'

import NavigationButton from './Navigation/NavigationButton.js'

import { GlobalState } from '../../../GlobalState.js'

export default {
    name: 'Navigation',
    components: {
          NavigationButton
    },
    props: {
    },
    template: `
        <div class="header-navigation" >
            <NavigationButton :text="'Articles'" :page="globalState.pagesDefinition.ARTICLES" />
            <NavigationButton v-if="globalState.isPurchaser()" :text="'My articles'" :page="globalState.pagesDefinition.MY_ARTICLES" />
            <NavigationButton v-if="globalState.isPurchaser()" :text="'My sold articles'" :page="globalState.pagesDefinition.SOLD_ARTICLES" />
            <NavigationButton :text="'My orders'" :page="globalState.pagesDefinition.MY_ORDERS" />
            <NavigationButton :text="'My cart'" :page="globalState.pagesDefinition.MY_CART" />
        </div>
    `,
    setup(props) {
        addCSS('./css/components/Header/RightHeader/Navigation.css'); /* Add the style-link for this component to the head */
        const globalState = ref(GlobalState);
        return { globalState };
    }
};