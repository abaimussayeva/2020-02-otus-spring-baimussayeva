import React from "react";
import Loader from 'react-loader-spinner'
import {usePromiseTracker} from 'react-promise-tracker'

const LoadingIndicator = props => {
    const {promiseInProgress} = usePromiseTracker();
    return (
        promiseInProgress &&
        <div style={{
            width: "100%",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            position: "absolute",
            left: "0",
            zIndex: "1000",
            height: "100%"
        }}>
            <Loader type="TailSpin" color="#2BAD60" height="100" width="100"/>
        </div>
    );
};
export default LoadingIndicator;
