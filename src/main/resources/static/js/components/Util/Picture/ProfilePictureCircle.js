import { ref,reactive } from '../../../vue/vue.esm-browser.prod.min.js'
import addCSS from '../../../AppUtils.js'

import { GlobalState } from '../../../GlobalState.js'

export default {
    name: 'ProfilePictureCircle',
    props: {
        imagePath: {
           type: String,
           required: true
        },
        width: {
            type: String,
            required: true
        },
        height: {
             type: String,
             required: true
        },
        toggleOnClick: {
            type: Boolean,
            required: true
        }
    },
    template: `
        <div class="profile-picture-circle" :style="circleStyle" >
            <img v-bind:src="imagePath" @click="toggle" ></img>
        </div>
    `,
    setup(props, { emit }) {
        addCSS('./css/components/Util/Picture/ProfilePictureCircle.css'); /* Add the style-link for this component to the head */
        const imagePath = ref(props.imagePath);
        /* Set the width and height from the properties */
        const circleStyle = reactive({
          width: props.width,
          height: props.height
        });

        /* Emit the toggled passes property object for use in parent */
        const toggle = () => {
            emit('update:toggleOnClick', !props.toggleOnClick);
        };

        return { imagePath, circleStyle, toggle};
    }
};