import {ADD_DOMAIN_NAME, ADD_NOTIFICATION, CHANGE_LOGIN, CHANGE_PAGE, DELETE_NOTIFICATION} from "../action-types"

const initialState = {
    state: {},
    activePage: "Login",
    domainName: "",
    loggedIn: false,
    newNotifications: false,
    friendNotifications: [],
    paymentNotifications: [],
};

function rootReducer(state = initialState, action) {
    if (action.type === ADD_DOMAIN_NAME) {
        return {
            ...state,
            domainName: action.payload.domainName
        }
    } else if (action.type === CHANGE_PAGE) {
        return {
            ...state,
            activePage: action.payload.activePage
        }
    } else if (action.type === CHANGE_LOGIN) {
        return {
            ...state,
            loggedIn: action.payload.loggedIn
        }
    } else if (action.type === ADD_NOTIFICATION) {
        return {
            ...state,
            newNotifications: true,
            [action.payload.key]: [action.payload.notification, ...state[action.payload.key]],
        }
    } else if (action.type === DELETE_NOTIFICATION) {
        return {
            ...state,
            [action.payload.key]: state[action.payload.key].filter((item, index) => {
                return index !== action.payload.index;
            })
        }
    }

    /*
    return Object.assign({}, state, {
      articles: state.articles.concat(action.payload)
    });
    */
    return state;
}

export default rootReducer;