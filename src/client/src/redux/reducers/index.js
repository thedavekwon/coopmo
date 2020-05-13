import {
  ADD_DOMAIN_NAME,
  ADD_NOTIFICATION,
  CHANGE_LOGIN,
  CHANGE_NEW_NOTIFICATIONS,
  CHANGE_PAGE,
  CHANGE_REFRESH_STATE,
  DELETE_NOTIFICATION,
  CHANGE_USERNAME,
} from "../action-types";

const initialState = {
  state: {},
  activePage: "Login",
  domainName: "",
  loggedIn: false,
  newNotifications: false,
  friendNotifications: [],
  paymentNotifications: [],
  refreshBalance: false,
  refreshFeed: false,
  refreshFriendsList: false,
  refreshFriendRequests: false,
  refreshProfilePic: false,
  username: "",
};

function rootReducer(state = initialState, action) {
  switch (action.type) {
    case ADD_DOMAIN_NAME:
      return {
        ...state,
        domainName: action.payload.domainName,
      };
    case CHANGE_PAGE:
      return {
        ...state,
        activePage: action.payload.activePage,
      };
    case CHANGE_LOGIN:
      return {
        ...state,
        loggedIn: action.payload.loggedIn,
      };
    case ADD_NOTIFICATION:
      return {
        ...state,
        newNotifications: true,
        [action.payload.key]: [
          action.payload.notification,
          ...state[action.payload.key],
        ],
      };
    case DELETE_NOTIFICATION:
      return {
        ...state,
        [action.payload.key]: state[action.payload.key].filter(
            (item, index) => {
              return index !== action.payload.index;
            }
        ),
      };
    case CHANGE_REFRESH_STATE:
      return {
        ...state,
        [action.payload.type]: action.payload.newState,
      }
    case CHANGE_NEW_NOTIFICATIONS:
      return {
        ...state,
        newNotifications: action.payload.newState,
      }
    case CHANGE_USERNAME:
      return {
        ...state,
        username: action.payload.username,
      }
    default:
      return state;
  }

}

export default rootReducer;
