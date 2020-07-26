import React from 'react';
import {withStyles} from '@material-ui/core/styles';
import {Button, FormControl, Grid, TextField, Typography} from '@material-ui/core';
import {login} from '../../util/APIUtils';
import {ACCESS_TOKEN} from '../../constants/constants.js';

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            login: '',
            password: '',
            error: false,
            errorMessage: '',
            loading: false,
            showPassword: false
        };
        this.login = this.login.bind(this);
        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.handleChangeLogin = this.handleChangeLogin.bind(this);
    }

    login = () => {
        const requestBody = {
            login: this.state.login,
            password: this.state.password
        };
        this.setState({loading: true});
        login(requestBody)
            .then(response => {
                localStorage.setItem(ACCESS_TOKEN, response.data['accessToken']);
                this.props.loadCurrentUser();
            })
            .catch(error => {
                this.setState({
                    error: true,
                    errorMessage: 'Ошибка авторизации',
                    loading: false
                });
            });
    };

    handleChangePassword = e => {
        this.setState({
            password: e.target.value,
        });
    };
    handleChangeLogin = e => {
        this.setState({
            login: e.target.value,
        });
    };
    keyPressEnter = e => {
        if (e.key === 'Enter') {
            this.login();
        }
    };


    render() {
        const {error, errorMessage} = this.state;
        const {classes} = this.props;

        return (
            <Grid container direction="column" className={classes.root}>
                <Grid item>
                    <Typography className={classes.title} variant="h6">
                        {'Аутентификация'}
                    </Typography>
                    <hr className={classes.divider}/>
                    <Grid
                        container
                        direction="column"
                        justify="center"
                        spacing={2}
                        className={classes.container}
                    >
                        <Grid item>
                            <FormControl fullWidth={true}>
                                <TextField
                                    variant="outlined"
                                    margin="dense"
                                    type="text"
                                    label={'Логин'}
                                    id="standart-login-input"
                                    autoComplete="current-password"
                                    onChange={this.handleChangeLogin}
                                    autoFocus={true}
                                />
                            </FormControl>
                        </Grid>
                        <Grid item>
                            <FormControl fullWidth={true}>
                                <TextField
                                    variant="outlined"
                                    margin="dense"
                                    type={'password'}
                                    label={'Пароль'}
                                    id="standart-password-input"
                                    autoComplete="current-password"
                                    onChange={this.handleChangePassword}
                                    error={error}
                                    helperText={errorMessage}
                                    onKeyPress={this.keyPressEnter}
                                    InputProps={{
                                        className: classes.inputWrapper,
                                    }}
                                />
                            </FormControl>
                        </Grid>
                        <Grid item container justify="center">
                            <Button variant="contained" onClick={this.login}>
                                {'Войти'}
                            </Button>
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        );
    }
}

const styles = theme => ({
    root: {
        minHeight: '100vh',
        width: 384,
        margin: 'auto',
        padding: theme.spacing(5),
    },
    container: {
        flexGrow: 1,
    },
    title: {
        textAlign: 'center',
        fontSize: 20,
        fontWeight: 500,
        color: theme.palette.primary.dark,
    },
    divider: {
        height: 1,
        margin: theme.spacing(2.5, 3),
        border: 'none',
        alignSelf: 'stretch',
        background: 'rgba(0, 0, 0, 0.12)',
    },
    inputWrapper: {
        padding: 0,
    }
});

export default withStyles(styles)(Login);
