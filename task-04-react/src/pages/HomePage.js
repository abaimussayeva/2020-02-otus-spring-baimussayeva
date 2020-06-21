import React, {Component} from "react";
import {Grid, withStyles} from "@material-ui/core";
import {combineClassName} from "../util/Utils";

import NavBar from "../components/NavBar";
import BooksPage from "../components/BooksPage";

class HomePage extends Component{
    constructor(props) {
        super(props);
    }

    render() {
        const {classes} = this.props;

        return (
            <Grid container direction="column" className={classes.root}>
                <Grid item>
                    <NavBar
                        logout={this.props.logout}
                        authenticated={this.props.authenticated}
                    />
                </Grid>
                <div className={combineClassName(classes.grid, classes.container)}>
                    <BooksPage/>
                </div>
            </Grid>
        );
    }
}

const styles = theme => ({
    root: {
        height: '100vh',
        flexWrap: 'unset'
    },
    container: {
        flexGrow: 1
    },
    grid: {
        width: '100%',
        display: 'grid',
        gridTemplateRows: '100%',
        background: '#E8EAF6',
    },
    header: {
        zIndex: 2
    },
});

export default withStyles(styles)(HomePage);
