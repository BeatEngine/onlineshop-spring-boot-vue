import { ref } from '../vue/vue.esm-browser.prod.min.js'
import addCSS from '../AppUtils.js'

import LeftTopCorner from './Header/LeftTopCorner.js'
import RightHeader from './Header/RightHeader.js'
import RightTopCorner from './Header/RightTopCorner.js'

export default {
    name: 'Header',
    components: {
          LeftTopCorner,
          RightHeader,
          RightTopCorner
    },
    props: {
        headerTitle: {
            type: String,
            required: true
        }
    },
    template: `
        <div class="shop-header">
            <LeftTopCorner :headerTitle="headerTitle"/>
            <RightHeader />
            <RightTopCorner />
            <div class="shop-header-border-bottom"></div>
        </div>
    `,
    setup(props) {
        addCSS('./css/components/Header.css'); /* Add the style-link for this component to the head */
        const headerTitle = ref(props.headerTitle);
        return { headerTitle };
    }
};