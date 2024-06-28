import { ref, computed } from '../../../vue/vue.esm-browser.prod.min.js'
import addCSS from '../../../AppUtils.js'

import { GlobalState } from '../../../GlobalState.js'

import ProfilePictureCircle from '../../Util/Picture/ProfilePictureCircle.js'

export default {
    name: 'MyProfileButton',
    components: {
        ProfilePictureCircle
    },
    props: {
    },
    template: `
        <div class="my-profile-button">
            <ProfilePictureCircle :imagePath="noUserPicture" :width="'65px'" :height="'65px'"
            :toggleOnClick="GlobalState.dialogStates.myProfileMenuExpanded" @update:toggleOnClick="updateToggleOnClick" />
            <div class="my-profile-button-subject">{{subject}}</div>
            <div v-show="GlobalState.dialogStates.myProfileMenuExpanded">TODO IMPLEMENT</div>
        </div>
    `,
    setup(props) {
        addCSS('./css/components/Header/RightTopCorner/MyProfileButton.css'); /* Add the style-link for this component to the head */
        const subject = computed(() => GlobalState.accountDetails.displayName);
        /* Event handler to update the State in GlobalState */
        const updateToggleOnClick = (newValue) => {
            GlobalState.dialogStates.myProfileMenuExpanded = newValue;
        };

        const noUserPicture = './img/profile/no-user-picture.png';
        return { subject, noUserPicture, GlobalState, updateToggleOnClick };
    }
};