import {SET_USER} from '../actionTypes';

const initialState = {
    authenticated: false,
    data: null,
};

const userReducer = (state = initialState, action) => {
    switch (action.type) {
        case SET_USER:
            return {
                ...state,
                authenticated: action.payload !== null,
                data: action.payload,
            };
        default:
            return state;
    }
};

export default userReducer;
