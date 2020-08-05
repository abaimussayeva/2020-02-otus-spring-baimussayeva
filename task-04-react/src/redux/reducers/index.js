import {combineReducers} from 'redux';
import userReducer from './userReducer';
import {CLEAR_STORE} from "../actionTypes";

const appReducer = combineReducers({
    user: userReducer
});

const rootReducer = (state, action) => {
    if (action.type === CLEAR_STORE) {
        state = undefined;
    }
    return appReducer(state, action);
};

export default rootReducer;
