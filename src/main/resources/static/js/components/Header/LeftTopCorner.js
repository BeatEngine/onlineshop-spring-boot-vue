import { ref } from '../../vue/vue.esm-browser.prod.min.js'
import addCSS from '../../AppUtils.js'

export default {
    name: 'LeftTopCorner',
    props: {
        headerTitle: {
            type: String,
            required: true
        }
    },
    template: `
        <div class="header-left-top-corner">
                <span class="shop-header-title">{{insertTitle}}</span>
        </div>
    `,
    setup(props) {
        addCSS('./css/components/Header/LeftTopCorner.css'); /* Add the style-link for this component to the head */
        const insertTitle = ref(props.headerTitle);
        return { insertTitle };
    }
};