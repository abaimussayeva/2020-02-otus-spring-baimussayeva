import React from 'react';
import ReactDOM from 'react-dom';
import * as serviceWorker from './serviceWorker';
import IndexRouter from './routing/IndexRouter';
import 'fontsource-roboto/cyrillic.css';
import './index.css';
import LoadingIndicator from "./components/LoadingIndicator";

ReactDOM.render(
    <div>
        <LoadingIndicator/>
        <IndexRouter/>
    </div>
    ,
    document.getElementById('root')
);
serviceWorker.unregister();
