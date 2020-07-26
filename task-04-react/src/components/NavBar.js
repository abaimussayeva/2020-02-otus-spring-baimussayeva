import React, {Component} from "react";
import Typography from "@material-ui/core/Typography";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import {ExitToApp, LibraryBooks, PersonOutlined} from "@material-ui/icons";
import {withStyles} from "@material-ui/core";
import Avatar from "@material-ui/core/Avatar";
import IconButton from "@material-ui/core/IconButton";
import {ACCESS_TOKEN} from "../constants/constants";
import {clearStore} from "../redux/actions/appActions";
import {connect} from "react-redux";

class NavBar extends Component {
    constructor(props) {
        super(props);
        this.state = {
            anchorEl: null
        };
    }

    handleLogout = () => {
        localStorage.removeItem(ACCESS_TOKEN);
        this.props.clearStore();
    };

    render() {
        const {classes, currentUser} = this.props;
        return (
            <AppBar position="static" className={classes.appBar}>
                <Toolbar className={classes.appBar}>
                    <LibraryBooks />
                    <Typography variant="h6" className={classes.title}>
                        Библиотека
                    </Typography>
                    <div className={classes.account}>
                        <Avatar className={classes.avatar}>
                            <PersonOutlined />
                        </Avatar>
                        <Typography variant="body2">{currentUser.name}</Typography>
                        <IconButton className={classes.button} onClick={this.handleLogout}>
                            <ExitToApp />
                        </IconButton>
                    </div>
                </Toolbar>
            </AppBar>
        )
    }
}

const styles = theme => ({
    root: {
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
    },
    account: {
        gap: '16px',
        display: 'grid',
        gridTemplateColumns: 'repeat(3, max-content)',
        alignItems: 'center',
    },
    avatar: {
        width: 38,
        height: 38,
        background: theme.palette.primary.main,
        color: '#DADADA',
    },
    button: {
        color: '#DADADA',
    },
});

const mapStateToProps = state => ({
    currentUser: state.user.data,
});

const mapDispatchToProps = dispatch => ({
    clearStore: () => dispatch(clearStore()),
});

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(styles)(NavBar))