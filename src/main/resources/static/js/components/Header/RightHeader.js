import { ref } from '../../vue/vue.esm-browser.prod.min.js'
import addCSS from '../../AppUtils.js'

import Navigation from './RightHeader/Navigation.js'
import HeaderSearch from './RightHeader/HeaderSearch.js'

export default {
    name: 'RightHeader',
    components: {
          Navigation,
          HeaderSearch
    },
    props: {
    },
    template: `
        <div class="header-right">
                <Navigation />
                <HeaderSearch />
        </div>
    `,
    setup(props) {
        addCSS('./css/components/Header/RightHeader.css'); /* Add the style-link for this component to the head */

        return { };
    }
};