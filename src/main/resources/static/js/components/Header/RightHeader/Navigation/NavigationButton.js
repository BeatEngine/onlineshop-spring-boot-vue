import { ref } from '../../../../vue/vue.esm-browser.prod.min.js'
import addCSS from '../../../../AppUtils.js'

import { GlobalState } from '../../../../GlobalState.js'

export default {
    name: 'NavigationButton',
    props: {
        text: {
            type: String,
            required: true
        },
        page: {
            type: String,
            required: true
        }
    },
    template: `
        <div @click="loadPage" class="header-navigation-button">
            {{ text }}
        </div>
    `,
    setup(props, { emit }) {
        addCSS('./css/components/Header/RightHeader/Navigation/NavigationButton.css'); /* Add the style-link for this component to the head */

        const text = ref(props.text);

        const loadPage = function() {
            GlobalState.loadPage(props.page);
        };

        return { text, loadPage };
    }
};