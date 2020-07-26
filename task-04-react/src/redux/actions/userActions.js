import {SET_USER} from "../actionTypes";

export const setUser = (payload = null) => ({
    type: SET_USER,
    payload,
});
