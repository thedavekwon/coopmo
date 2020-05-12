import {ADD_DOMAIN_NAME, CHANGE_PAGE} from "../action-types"

const initialState = {
    state: {},
    activePage: "Login"
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
    }
    /*
    return Object.assign({}, state, {
      articles: state.articles.concat(action.payload)
    });
    */
    return state;
}

export default rootReducer;