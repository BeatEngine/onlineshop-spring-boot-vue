import { ref,reactive } from '../../../vue/vue.esm-browser.prod.min.js'
import addCSS from '../../../AppUtils.js'

export default {
    name: 'ClickableIconList',
    props: {
        listEntryData: {
            type: Array,
            required: false,
            default: [
                {
                    pathSymbol: './favicon.ico',
                    text: 'TODO Implement',
                    click() {
                        alert('TODO Implement');
                    }
                }, {
                  pathSymbol: './favicon.ico',
                  text: 'TODO Implement 2',
                  click() {
                      alert('TODO Implement 2');
                  }
                }
            ]
        }
    },
    template: `
        <ul class="clickable-icon-list" >
            <li v-for="e in entries" @click="e.click" ><img :src="e.pathSymbol" class="clickable-icon-list-icon"></img><span class="clickable-icon-list-text">{{e.text}}</span></li>
        </ul>
    `,
    setup(props, { emit }) {
        addCSS('./css/components/Util/List/ClickableIconList.css'); /* Add the style-link for this component to the head */
        const entries = reactive(props.listEntryData);
        return { entries };
    }
};