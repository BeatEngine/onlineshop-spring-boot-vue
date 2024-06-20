import { ref } from '../../vue/vue.esm-browser.prod.min.js'
import addCSS from '../../AppUtils.js'

export default {
    name: 'SoldArticles',
    components: {
    },
    props: {
    }, /*HERE are all views of the SPA defined loaded when the property currentPage changes*/
    template: `
        <div class="main-sold-articles">
            <h1>TODO: Implement SoldArticles</h1>
        </div>
    `,
    setup(props) {
        addCSS('./css/components/MainContent/SoldArticles.css'); /* Add the style-link for this component to the head */

        return {  };
    }
};