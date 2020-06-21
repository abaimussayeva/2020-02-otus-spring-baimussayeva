import React, {Component} from "react";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import HomePage from "../pages/HomePage";
import 'react-s-alert/dist/s-alert-default.css';
import 'react-s-alert/dist/s-alert-css-effects/slide.css';
import {createMuiTheme, ThemeProvider} from "@material-ui/core";

class IndexRouter extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }
    render() {
        return (
            <ThemeProvider theme={theme}>
                <Router>
                    <Switch>
                        <Route path="/">
                            <HomePage />
                        </Route>
                    </Switch>
                </Router>
            </ThemeProvider>
        );
    }
}

const theme = createMuiTheme();

export default IndexRouter;
