import React from 'react';
import ReactDOM from 'react-dom';
import * as serviceWorker from './serviceWorker';
import IndexRouter from './routing/IndexRouter';
import 'fontsource-roboto/cyrillic.css';
import './index.css';
import LoadingIndicator from "./components/LoadingIndicator";
import {createMuiTheme} from "@material-ui/core";
import {ThemeProvider} from "@material-ui/styles";
import {Provider} from 'react-redux';
import {PersistGate} from 'redux-persist/integration/react';
import {persistor, store} from "./redux/store";

ReactDOM.render(
    <ThemeProvider theme={createMuiTheme()}>
        <LoadingIndicator />
        <Provider store={store}>
            <PersistGate loading={null} persistor={persistor}>
                <IndexRouter />
            </PersistGate>
        </Provider>
    </ThemeProvider>,
    document.getElementById('root')
);
serviceWorker.unregister();
