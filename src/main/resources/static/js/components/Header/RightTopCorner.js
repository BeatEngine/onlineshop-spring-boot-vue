import { ref } from '../../vue/vue.esm-browser.prod.min.js'
import addCSS from '../../AppUtils.js'

import MyProfileButton from './RightTopCorner/MyProfileButton.js'

export default {
    name: 'RightTopCorner',
    components: {
        MyProfileButton
    },
    props: {
    },
    template: `
        <div class="header-right-top-corner">
                <MyProfileButton />
        </div>
    `,
    setup(props) {
        addCSS('./css/components/Header/RightTopCorner.css'); /* Add the style-link for this component to the head */

        return { };
    }
};