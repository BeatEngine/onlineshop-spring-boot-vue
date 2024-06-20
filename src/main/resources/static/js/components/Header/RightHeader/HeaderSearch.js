import { ref } from '../../../vue/vue.esm-browser.prod.min.js'
import addCSS from '../../../AppUtils.js'

export default {
    name: 'HeaderSearch',
    props: {
    },
    template: `
        <div class="header-search">
                TODO: implement SEARCH
        </div>
    `,
    setup(props) {
        addCSS('./css/components/Header/RightHeader/HeaderSearch.css'); /* Add the style-link for this component to the head */

        return { };
    }
};