import { ref } from '../vue/vue.esm-browser.prod.min.js'
import addCSS from '../AppUtils.js'
/* TODO Implement a dynamic loaded GUI for the List-Elements IF we are at the page products we show ProductFilter else something different etc. */
export default {
    name: 'SidebarLeft',
    props: {
        todoMakeThisStuffLater: {
            type: String,
            required: true
        }
    },
    template: `
        <div class="shop-sidebar-left">
            <div>TODO ProductFilter Render that IF we are on page products</div>
        </div>
    `,
    setup(props) {
        addCSS('./css/components/SidebarLeft.css'); /* Add the style-link for this component to the head */
        const todoMakeThisStuffLater = ref(props.todoMakeThisStuffLater);
        return { todoMakeThisStuffLater };
    }
};