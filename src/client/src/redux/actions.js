import {
  ADD_DOMAIN_NAME,
  ADD_NOTIFICATION,
  CHANGE_LOGIN,
  CHANGE_PAGE,
  CHANGE_REFRESH_STATE,
  DELETE_NOTIFICATION
} from "./action-types";

export const addDomainName = (domainName) => ({
  type: ADD_DOMAIN_NAME,
  payload: {
    id: 0,
    domainName,
  },
});

export const changePage = (activePage) => ({
  type: CHANGE_PAGE,
  payload: {
    activePage,
  },
});

export const changeLogin = (loggedIn) => ({
  type: CHANGE_LOGIN,
  payload: {
    loggedIn,
  },
});

export const addNotification = (notification) => {
  let key;
  if (notification.type === "FRIENDREQUEST" || notification.type === "FRIENDACCEPT") {
    key = "friendNotifications"
  } else {
    key = "paymentNotifications"
  }
  return ({
    type: ADD_NOTIFICATION,
    payload: {
      key,
      notification,
    },
  });
}

export const deleteNotification = (index, notification) => {
  let key;
  if (notification.type === "FRIENDREQUEST" || notification.type === "FRIENDACCEPT") {
    key = "friendNotifications"
  } else {
    key = "paymentNotifications"
  }
  return ({
    type: DELETE_NOTIFICATION,
    payload: {
      index,
      key
    }
  })
}

export const changeRefreshState = (type, newState) => {
  return ({
    type: CHANGE_REFRESH_STATE,
    payload: {
      type,
      newState
    }
  })
}
