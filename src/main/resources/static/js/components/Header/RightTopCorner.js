import { ref } from '../../vue/vue.esm-browser.prod.min.js'
import addCSS from '../../AppUtils.js'

export default {
    name: 'RightTopCorner',
    props: {
    },
    template: `
        <div class="header-right-top-corner">
                TODO: implement USER-PROFILE
        </div>
    `,
    setup(props) {
        addCSS('./css/components/Header/RightTopCorner.css'); /* Add the style-link for this component to the head */

        return { };
    }
};