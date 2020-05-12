import {ADD_DOMAIN_NAME, CHANGE_PAGE} from "./action-types"

export const addDomainName = domainName => ({
    type: ADD_DOMAIN_NAME,
    payload: {
        id: 0,
        domainName
    }
})

export const changePage = activePage => ({
    type: CHANGE_PAGE,
    payload: {
        activePage
    }
})