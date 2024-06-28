import { createApp, ref } from './vue/vue.esm-browser.prod.min.js'

import Header from './components/Header.js'
import SidebarLeft from './components/SidebarLeft.js'
import MainContent from './components/MainContent.js'

import addCSS from './AppUtils.js'

import { GlobalState } from './GlobalState.js'



createApp({
  components: {
      Header,
      SidebarLeft,
      MainContent
    },
    template: `
      <div class="app-container">
        <Header :headerTitle="headerTitle" />
        <SidebarLeft />
        <MainContent />
      </div>
    `,
    setup() {
           //TODO Implement all the tabs for the views and only show the search on products and my-articles
        /* Load the account details from the server. */
        const func = async function() {
            await GlobalState.loadAccountDetails();
        };
        func();

        const headerTitle = ref('Demo Online-Shop');
        return { headerTitle };
    }
}).mount('#app');