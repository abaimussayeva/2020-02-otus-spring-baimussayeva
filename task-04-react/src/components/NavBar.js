import React, {Component} from "react";
import Typography from "@material-ui/core/Typography";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import {LibraryBooks} from "@material-ui/icons";
import {withStyles} from "@material-ui/core";

class NavBar extends Component {
    constructor(props) {
        super(props);
        this.state = {
            anchorEl: null
        };
    }

    render() {
        const {classes} = this.props;
        return (
            <AppBar position="static" className={classes.appBar}>
                <Toolbar className={classes.appBar}>
                    <LibraryBooks />
                    <Typography variant="h6" className={classes.title}>
                        Библиотека
                    </Typography>
                </Toolbar>
            </AppBar>
        )
    }
}

const styles = theme => ({root: {
        flexGrow: 1,
    },
    appBar: {
        height: 56,
        minHeight: 56,
        padding: 0,
        margin: 0,
        paddingLeft: 10
    },
    title: {
        marginLeft: 12,
        flexGrow: 1,
        fontSize: 16,
        fontWeight: 400
    },
    icon: {
        backgroundImage: 'url(assets/images/logo.png)',
        backgroundSize: 'cover',
        height: 24,
        width: 28
    }
});

export default withStyles(styles)(NavBar);