import {
  ADD_DOMAIN_NAME,
  ADD_NOTIFICATION,
  CHANGE_LOGIN,
  CHANGE_MENU_PAGE,
  CHANGE_NEW_NOTIFICATIONS,
  CHANGE_PAGE,
  CHANGE_REFRESH_STATE,
  DELETE_NOTIFICATION,
  CHANGE_USERNAME,
} from "../action-types";
import {PURGE} from "redux-persist";

const initialState = {
  activePage: "Login",
  domainName: "http://localhost:8080",
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
  activeMenuPage: "Edit Profile"
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

    case PURGE:
      return initialState;
    case CHANGE_MENU_PAGE:
      return {
        ...state,
        activeMenuPage: action.payload.newPage,
        activePage: "MenuPage",
      }
    default:
      return state;
  }

}

export default rootReducer;
