import React, {Component} from "react";
import {BrowserRouter as Router, Redirect, Route, Switch} from 'react-router-dom';
import HomePage from "../pages/HomePage";
import {connect} from 'react-redux';
import {setUser} from "../redux/actions/userActions";
import {clearStore} from "../redux/actions/appActions";
import {getUser} from "../util/APIUtils";
import Login from "../pages/login/Login";

class IndexRouter extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    loadCurrentlyLoggedInUser = () => {
        getUser()
            .then(response => {
                this.props.setUser(response.data);
            })
            .catch(error => {
                this.props.setUser();
            });
    };

    render() {
        return (
            <Router>
                <Switch>
                    <Route
                        exact
                        path="/login"
                        render={props =>
                            this.props.authenticated ? (
                                <Redirect to="/" />
                            ) : (
                                <Login
                                    {...props}
                                    loadCurrentUser={this.loadCurrentlyLoggedInUser}
                                />
                            )
                        }
                    />
                    <Route
                        path="/"
                        render={() =>
                            this.props.authenticated ? (
                                <HomePage />
                            ) : (
                                <Redirect to="/login" />
                            )
                        }
                    />
                </Switch>
            </Router>
        );
    }
}

const mapStateToProps = state => ({
    authenticated: state.user.authenticated,
    currentUser: state.user.data,
});

const mapDispatchToProps = dispatch => ({
    setUser: data => dispatch(setUser(data)),
    clearStore: () => dispatch(clearStore()),
});

export default connect(mapStateToProps, mapDispatchToProps)(IndexRouter)
